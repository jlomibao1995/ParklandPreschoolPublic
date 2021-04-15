package servlets.guardian;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.Child;
import problemdomain.Classroom;
import problemdomain.PaymentDetails;
import problemdomain.Registration;
import services.ChildService;
import services.ClassroomService;
import services.PaymentDetailsService;
import services.RegistrationService;

/**
 * Controller for creating a registration for a child
 * @author Jean Lomibao
 */
public class RegisterChildServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Grabs the child to be registered and classes that the child can be registered in
     * then loads the RegisterChildUi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get the child to be registered
        String childId = request.getParameter("childId");
        String goTo = request.getParameter("goTo");
        ChildService childService = new ChildService();

        try {
            Child child = childService.getChild(childId);

            //get classes child can get registered to
            ClassroomService classService = new ClassroomService();
            List<Classroom> classes = classService.getClassroomsForChild(child);

            request.setAttribute("child", child);
            request.setAttribute("classes", classes);
            request.setAttribute("mode", "fees");

            //when user clicks next button the previously chosen class is reflected back
            //the right tab is then activated
            if (goTo != null) {
                String classPicked = request.getParameter("classPicked");
                request.setAttribute("classPicked", classPicked);

                if (classPicked.equals("undefined")) {
                    request.setAttribute("classPicked", new Long(0));
                }

                switch (goTo) {
                    case "classes":
                        request.setAttribute("mode", "classes");
                        break;
                    case "guardian":
                        request.setAttribute("mode", "guardian");
                        break;
                    case "policies":
                        request.setAttribute("mode", "policies");
                        break;
                }
            }

            getServletContext().getRequestDispatcher("/WEB-INF/guardian/RegisterChildUi.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegisterChildServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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

    /**
     * {@inheritDoc }
     * Creates the registration for the child using the class chosen then sends
     * user to the right page depending on if they are wait listed or a spot is
     * available 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get the user inputs
        String classId = request.getParameter("classChosen");
        String childId = request.getParameter("childId");
        
        //get signatures and date stamps
        String safetySignature = request.getParameter("safetySignature");
        String outdoorSignature = request.getParameter("outdoorSignature");
        String disciplineSignature = request.getParameter("disciplineSignature");
        String sickSignature = request.getParameter("sickSignature");

        //register the child
        RegistrationService registrationService = new RegistrationService();

        String status;
        try {
            String path = getServletContext().getRealPath("/WEB-INF");
            status = registrationService.registerChild(childId, classId, path, 
                    safetySignature, outdoorSignature, disciplineSignature, sickSignature);

            String registrationId = status.substring(1);

            //if returned string has the W character then child has been waitlisted otherwise
            //user is sent to the checkout page to proceed with the registration fee payment
            if (status.substring(0, 1).equals("W")) {
                Registration registration = registrationService.getRegistration(registrationId);
                request.setAttribute("registration", registration);
                getServletContext().getRequestDispatcher("/WEB-INF/guardian/WaitList.jsp").forward(request, response);
            } else {
                request.setAttribute("paymentId", status);
                PaymentDetailsService paymentServices = new PaymentDetailsService();
                PaymentDetails payment = paymentServices.getPaymentDetails(status);
                request.setAttribute("payment", payment);
                getServletContext().getRequestDispatcher("/WEB-INF/guardian/Checkout.jsp").forward(request, response);
            }
            //business rules that are not met throw an IllegalArgumentException
            //exceptions are caught and display the right message 
        } catch (IllegalArgumentException ex){ 
            //provide information and display error message
            request.setAttribute("safetySignature", safetySignature);
            request.setAttribute("outdoorSignature", outdoorSignature);
            request.setAttribute("disciplineSignature", disciplineSignature);
            request.setAttribute("sickSignature", sickSignature);
            request.setAttribute("classPicked", classId);
            
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());
            
            doGet(request, response);
        }catch (Exception ex) {
            Logger.getLogger(RegisterChildServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
