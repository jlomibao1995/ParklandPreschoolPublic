package servlets.admin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.Registration;
import services.RegistrationService;

/**
 * Controller for updating, reading, and deleting registrations
 *
 * @author Jean Lomibao
 */
public class RegistrationsServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Loads the RegistrationsUi with registration lists
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //get the search criteria
            String pendingKey = request.getParameter("pendingKey");
            String activeKey = request.getParameter("activeKey");
            String inactiveKey = request.getParameter("inactiveKey");

            //get list of pending registrations
            RegistrationService registrationService = new RegistrationService();
            List<Registration> pendingRegistrations = registrationService.getPendingRegistrations(pendingKey);
            request.setAttribute("pendingRegistrations", pendingRegistrations);

            //get list of active registrations and classrooms
            List<Registration> activeRegistrations = registrationService.getRegistrationsByActiveStatus(true, activeKey);
            request.setAttribute("activeRegistrations", activeRegistrations);

            //get list of inactive registration
            List<Registration> inactiveRegistrations = registrationService.getRegistrationsByActiveStatus(false, inactiveKey);
            request.setAttribute("inactiveRegistrations", inactiveRegistrations);

            //get mode to populate the right page
            String mode = request.getParameter("mode");
            //get registration id if available
            String registrationId = request.getParameter("registrationId");
            Registration registration = null;

            if (registrationId != null && !registrationId.equals("")) {
                registration = registrationService.getRegistration(registrationId);
            }

            //depending on which tab was selected load the registration 
            if (mode != null) {
                switch (mode) {
                    case "pending":
                        request.setAttribute("registrationP", registration);
                        break;
                    case "active":
                        request.setAttribute("registrationA", registration);
                        break;
                    case "inactive":
                        request.setAttribute("registrationI", registration);

                }
            } else {
                request.setAttribute("mode", "pending");
            }

            request.setAttribute("mode", mode);
            getServletContext().getRequestDispatcher("/WEB-INF/admin/RegistrationsUi.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationsServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * Performs update and delete operations on Registrations using the
     * RegistrationService
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String registrationId = request.getParameter("registrationId");

        RegistrationService registrationService = new RegistrationService();
        try {
            switch (action) {
                //use the action parameter to determine the action the user is trying to do
                //use the registrationService to perform the registration operations
                //when operation is executed successfully provide a message for success
                //then send to the right tab
                case "accept":
                    registrationService.acceptRegistration(registrationId);
                    request.setAttribute("message", "Registration has been accepted");
                    request.setAttribute("success", true);
                    break;
                case "deny":
                    registrationService.denyRegistration(registrationId);
                    request.setAttribute("message", "Registration has been denied");
                    request.setAttribute("success", true);
                    break;
                case "activate":
                    registrationService.activateRegistration(registrationId);
                    request.setAttribute("message", "Registration has been activated");
                    request.setAttribute("success", true);
                    break;
                case "deactivate":
                    registrationService.deActivateRegistration(registrationId);
                    request.setAttribute("message", "Registration has been deactivated");
                    request.setAttribute("success", true);
                    break;
                case "delete":
                    registrationService.deleteRegistration(registrationId);
                    request.setAttribute("message", "Registration has been deleted");
                    request.setAttribute("success", true);
                    break;
            }

            request.setAttribute("mode", request.getParameter("mode"));
            doGet(request, response);

            //if the registrationId is null a failed message is provided
        } catch (NumberFormatException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Please select a registration to process in the right window");
            request.setAttribute("mode", request.getParameter("mode"));
        } catch (IllegalArgumentException ex) {
            //business rules that are not met throw an IllegalArgumentException
            //exceptions are caught and display the right message 
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("mode", request.getParameter("mode"));
        } catch (Exception ex) {
            Logger.getLogger(RegistrationsServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U#500";
            if (ex.getMessage().equals("Database error")) {
                errorCode = "D#400";
            }

            response.sendRedirect("/error?errorCode=" + errorCode);
            return;
        }
        
        //send back to doGet to grab lists of registrations
        doGet(request, response);
    }
}
