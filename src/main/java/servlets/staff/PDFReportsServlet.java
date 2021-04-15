package servlets.staff;

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
import services.ReportService;

/**
 * Controller for generating the registration PDF for the child
 * @author Jean Lomibao
 */
public class PDFReportsServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * {@inheritDoc}
     * Loads the registration information onto the preschool's registration form
     * in PDF format using the ReportService
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //initialize variables to be used
        String action = request.getParameter("action");
        ReportService reportService = new ReportService();
        String path = getServletContext().getRealPath("/");
        byte[] pdfData = null;
        RegistrationService registrationService;
        try {
            switch (action) {
                //use the action parameter to determine the action the user is trying to do
                //use the ReportService to perform the operations
                case "printRegistration":
                    String registrationId = request.getParameter("registrationId");
                    pdfData = reportService.getRegistrationForm(registrationId, path);
                    break;
                case "printAllActiveRegistrations":
                    registrationService = new RegistrationService();
                    List<Registration> activeRegistrations = registrationService.getRegistrationsByActiveStatus(true, null);
                    pdfData = reportService.printAllRegistrationForms(path, activeRegistrations);

                    break;
                case "printAllPendingRegistrations":
                    registrationService = new RegistrationService();
                    List<Registration> pendingRegistrations = registrationService.getPendingRegistrations("");
                    pdfData = reportService.printAllRegistrationForms(path, pendingRegistrations);
                    break;
            }
            response.setContentType("application/pdf");
            response.getOutputStream().write(pdfData);

        } catch (Exception ex) {
            Logger.getLogger(PDFReportsServlet.class.getName()).log(Level.SEVERE,"Server error", ex);
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
