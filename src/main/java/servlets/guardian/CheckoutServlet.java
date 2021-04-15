package servlets.guardian;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.PaymentDetails;
import services.PaymentDetailsService;

/**
 * Controller for displaying registration fee payment checkout
 * @author Jean
 */
public class CheckoutServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Grabs the payment id then grabs the payment object to be displayed
     * using the PaymentDetailsService then loads the CheckoutUi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        PaymentDetailsService paymentServices = new PaymentDetailsService();
        PaymentDetails payment;
        try {
            payment = paymentServices.getPaymentDetails(paymentId);
            
            //display message if payment has already been paid
            if (payment.getPaymentStatus() == 'P'){
                throw new IllegalArgumentException("Fee has already been paid");
            }
            
            request.setAttribute("payment", payment);
            request.setAttribute("paymentId", paymentId);
            getServletContext().getRequestDispatcher("/WEB-INF/guardian/Checkout.jsp").forward(request, response);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());
        } catch (Exception ex) {           
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
