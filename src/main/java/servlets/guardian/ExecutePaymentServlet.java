package servlets.guardian;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.PaymentDetailsService;
import services.PaypalService;

/**
 * Controller for processing payment through PayPal
 * @author Jean
 */
public class ExecutePaymentServlet extends HttpServlet {

    /**
     * {@inheritDoc}
     * Process get request the same way as a post request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }


    /**
     * {@inheritDoc }
     * Grabs the payment id and payer id then executes the payment through the
     * PayPal service
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");
        
        try {
            //use the payment id and payer id provided by the PayPal API to execute
            //the payment through the PayPalService class
            PaypalService paypalService = new PaypalService();
            Payment payment = paypalService.executePayment(paymentId, payerId); 
            
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            
            //update the payment object in the database and send email to user
            //using the PaymentDetailsService class
            PaymentDetailsService paymentService = new PaymentDetailsService();
            String path = getServletContext().getRealPath("/WEB-INF");
            paymentService.updateDBAfterPayment(transaction, payment, payerInfo, path);
            
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.setAttribute("payment", payment);
            
            getServletContext().getRequestDispatcher("/WEB-INF/guardian/Receipt.jsp").forward(request, response);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(ExecutePaymentServlet.class.getName()).log(Level.SEVERE, "PayPal API error", ex);
            //the error code provided denotes an error with paypal
            response.sendRedirect("/error?errorCode=P400");
        } catch (Exception ex) {
            Logger.getLogger(ExecutePaymentServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U500";
            if (ex.getMessage().equals("Database error")) {
                errorCode = "D400";
            }

            response.sendRedirect("/error?errorCode=" + errorCode);
        }
    }

}
