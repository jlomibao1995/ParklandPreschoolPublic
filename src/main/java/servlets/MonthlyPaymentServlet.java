package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.Registration;
import services.PaymentDetailsService;
import services.RegistrationService;

/**
 * Creates a payment object for registrations every month
 * @author Jean Lomibao
 */
public class MonthlyPaymentServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Uses the registration service to create a payment for the current month 
     * for each registered child
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //grab the given secret code from the request
        String secret = request.getParameter("secret");
        
        try {
            //check if the secret code matches then perform the payment creation
            if (secret.equals("")){
            //use the registration service to go through each registered child 
            //to create the monthly payment for the current month
            RegistrationService registrationService = new RegistrationService();
            PaymentDetailsService paymentService = new PaymentDetailsService();
            List<Registration> registrations = registrationService.getRegistrationsByActiveStatus(true, null);
            
            for (Registration registration: registrations){
                if (registration.getRegistrationStatus() == 'R'){
                    paymentService.createMonthlyPayment(registration);
                }
            }
        }
        } catch (Exception ex){
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U500";
            if (ex.getMessage().equals("Database error")){
                errorCode = "D400";
            } 
            
            Logger.getLogger(MonthlyPaymentServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            response.sendRedirect("/error?errorCode=" + errorCode);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
