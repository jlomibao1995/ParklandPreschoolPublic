package services;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import problemdomain.Account;
import problemdomain.PaymentDetails;

/*
 * Connects to the Paypal server and processes payments
 * @author Jean Lomibao
 */
public class PaypalService {
    private static final String CLIENT_ID ="";
    private static final String CLIENT_SECRET = "";
    private static final String MODE = "sandbox";
    private static final String TAX = "0.00";
    private static final String DESCRIPTION = "Parkland Prechool payment";
    
    /**
     * Takes in the payment details and connects to the PayPal server to create a transaction
     * @param paymentDetails details for what is being paid for
     * @param account account of user logged in
     * @return link to review payment with payment information
     * @throws PayPalRESTException thrown for PayPal related problem
     * @throws javax.naming.NamingException thrown if the environment variable is not found
     */
    public String authorizePayment(PaymentDetails paymentDetails, Account account) throws PayPalRESTException, NamingException {
        //make payer information to send to Paypal
        Payer payer = getPayerInformation(account);
        
        //specify url for cancelling order or coninuing an order 
        RedirectUrls redirectUrls = getRedirectUrls(paymentDetails);
        
        //grab the list of transactions
        List<Transaction> transactions = getTransactionInformation(paymentDetails);
        
        //create payment with all the needed information to be sent to PayPal
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(transactions)
                      .setRedirectUrls(redirectUrls)
                      .setPayer(payer)
                      .setIntent("authorize"); 
        
        //create API context to connect to Paypal server
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);
        
        //user will be redirected to the link provided by PayPal
        return getApprovalLink(approvedPayment);
    }
    
    /**
     * Creates Payer object that will contain the payer's information
     * @param account account of user logged in 
     * @return payer information
     */
    public Payer getPayerInformation(Account account){
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        
        //enter payer info using the account information
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(account.getAccountFirstName());
        payerInfo.setLastName(account.getAccountLastName());
        payerInfo.setEmail(account.getEmail());
        
        //set up billing address
        Address address = new Address();
        address.setCity(account.getAccountCity());
        address.setCountryCode("CA");
        address.setLine1(account.getAccountAddress());
        address.setPostalCode(account.getAccountPostalCode());
        payerInfo.setBillingAddress(address);
        payer.setPayerInfo(payerInfo);
        return payer;
    }
    
    /**
     * Gives PayPal links for canceling or continuing the process
     * @param paymentDetails payment information
     * @return url object containing the links
     */
    public RedirectUrls getRedirectUrls(PaymentDetails paymentDetails){
        RedirectUrls redirectUrls = new RedirectUrls();
        
        //specify url to use
        redirectUrls.setCancelUrl("http://localhost:8084/payregistration?paymentId=" + paymentDetails.getPaymentId());
        redirectUrls.setReturnUrl("http://localhost:8084/reviewpayment");
        return redirectUrls;
    }
    
    /**
     * Creates transaction for each item to be paid
     * @param paymentDetails contains the all the payment information of whats to be paid
     * @return list all the transactions to be processed 
     */
    public List<Transaction> getTransactionInformation(PaymentDetails paymentDetails){
        //convert price to text so PayPal will accept
        String subtotal = String.format("%.2f", paymentDetails.getPaymentSubtotal());
        String total = String.format("%.2f", paymentDetails.getPaymentTotal());
        
        //store item prices to be paid for in object
        Details details = new Details();
        details.setShipping("0");
        details.setSubtotal(subtotal);
        details.setTax(TAX);
        
        //set the currency and amounts to be paid
        Amount amount = new Amount();
        amount.setCurrency("CAD");
        amount.setTotal(total);
        amount.setDetails(details);
        
        //create a transaction 
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(DESCRIPTION);
        transaction.setInvoiceNumber(paymentDetails.getInvoiceId());
        
        //list for containg transaction items 
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        
        //store each item in the transaction 
        Item item = new Item();
        item.setCurrency("CAD").setName(DESCRIPTION)
                               .setPrice(subtotal)
                               .setTax(TAX)
                               .setQuantity("1");
        
        //add items in the transaction
        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);
        
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        
        return transactions;
    }
    
    /**
     * Generates the link to continue the payment process after payment has been authorized
     * @param approvedPayment authorized payment
     * @return link containing parameters to be used for processing payment
     */
    public String getApprovalLink(Payment approvedPayment){
        List<Links> links = approvedPayment.getLinks();
        
        for (Links link: links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                return link.getHref();
            }
        }
        return null;
    }
    
    /**
     * Completes the payment process and returns the payment details of the transaction
     * @param paymentId identifies the payment 
     * @param payerId identifies the payer
     * @return object containing payment information
     * @throws PayPalRESTException when there is a problem related to PayPal
     */
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        //create object for processing the payment 
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        
        Payment payment = new Payment().setId(paymentId);
        
        //connect to PayPal server to process payment
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(apiContext, paymentExecution);
    }
    
    /**
     * Returns the payment details of a payment
     * @param paymentId identifies a payment object
     * @return object containing payment information
     * @throws PayPalRESTException when there is a problem related to PayPal
     */
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }
}
