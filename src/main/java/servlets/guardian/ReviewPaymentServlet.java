package servlets.guardian;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.PaymentDBBroker;
import problemdomain.PaymentDetails;
import services.PaypalService;

/**
 * Controller for reviewing payment 
 * @author Jean Lomibao
 */
public class ReviewPaymentServlet extends HttpServlet {

    /**
     * {@inheritDoc}
     * Grabs the payment id and payer id sent from the PayPal API then sends the
     * information to the ExecutePaymentServlet to be processed
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");
        
        try {
            //retrieve payment instance from PayPal
            PaypalService paypalService = new PaypalService();
            Payment payment = paypalService.getPaymentDetails(paymentId);
            
            //grab the payer's information and send to the page
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            
            PaymentDBBroker paymentDB = new PaymentDBBroker();
            PaymentDetails paymentDetails = paymentDB.getPaymentByInvoice(transaction.getInvoiceNumber());
            
            //check if payment has already been paid
            if (paymentDetails.getPaymentStatus() == 'P'){
                throw new IllegalArgumentException("Fee has already been paid");
            }
            
            ShippingAddress address = transaction.getItemList().getShippingAddress();
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.setAttribute("address", address);
            
            response.sendRedirect("/executepayment?paymentId=" + paymentId +"&PayerID=" + payerId);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(ReviewPaymentServlet.class.getName()).log(Level.SEVERE, "PayPal API error", ex);
            //the error code provided denotes an error with paypal
            request.setAttribute("errorCode", "P400");
            response.sendRedirect("error");
        } catch (Exception ex) {
            Logger.getLogger(ReviewPaymentServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U500";
            if (ex.getMessage().equals("Database error")) {
                errorCode = "D400";
            } else if (ex instanceof IllegalArgumentException){                
                errorCode = "P401";
            }

            response.sendRedirect("/error?errorCode=" + errorCode);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}