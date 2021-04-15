package servlets.guardian;

import com.paypal.base.rest.PayPalRESTException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import problemdomain.PaymentDetails;
import services.AccountService;
import services.PaymentDetailsService;
import services.PaypalService;

/**
 * Controller for authorizing PayPal payments of registrations
 * @author Jean Lomibao
 */
public class AuthorizePaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * {@inheritDoc }
     * Grabs the account id and payment id then authorizes the payment 
     * using the PayPalService then loads the PayPal UI
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            //get account
            HttpSession session = request.getSession();
            String accountId = (String) session.getAttribute("accountId");
            AccountService accountService = new AccountService();

            Account account = accountService.getAccount(accountId);

            String paymentId = request.getParameter("paymentId");
            PaymentDetailsService paymentServices = new PaymentDetailsService();
            
            //process payment with paypal
            PaymentDetails paymentDetails = paymentServices.getPaymentDetails(paymentId);
            PaypalService paypalService = new PaypalService();
            String approvalLink = paypalService.authorizePayment(paymentDetails, account);
            //send user to the url provided by paypal
            response.sendRedirect(approvalLink);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(AuthorizePaymentServlet.class.getName()).log(Level.SEVERE, "PayPal API error", ex);
            //the error code provided denotes an error with paypal
            response.sendRedirect("error?errorCode=P400");
        } catch (Exception ex) {          
            Logger.getLogger(AuthorizePaymentServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
