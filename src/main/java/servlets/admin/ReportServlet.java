package servlets.admin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.Classroom;
import services.AccountService;
import services.ChildService;
import services.ClassroomService;
import services.PaymentDetailsService;
import services.RegistrationService;

/**
 * Controller for handling report generating operations
 * @author Jean Lomibao
 * @author Nic Kelly
 */
public class ReportServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Loads the ReportsUi with the list of database objects using the services
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //get the list of all objects in the database using the right services
            AccountService accountService = new AccountService();
            ClassroomService classManager = new ClassroomService();
            RegistrationService registrationManager = new RegistrationService();
            PaymentDetailsService paymentManager = new PaymentDetailsService();
            ChildService childManager = new ChildService();

            List accountList = accountService.getAllAccounts();
            List classList = classManager.getAllClassrooms();
            List registrationList = registrationManager.getAllRegistrations();
            List paymentList = paymentManager.getAllPayments();
            List childList = childManager.getAllChildren();
            List<String> months = paymentManager.getDistinctMonths();

            request.setAttribute("accountList", accountList);
            request.setAttribute("childList", childList);
            request.setAttribute("classList", classList);
            request.setAttribute("registrationList", registrationList);
            request.setAttribute("paymentList", paymentList);
            request.setAttribute("months", months);

            //determines the period criteria for the list of new accounts or active accounts to be displayed
            String newPeriod = "week";
            if (request.getParameter("newPeriod") != null) {
                newPeriod = request.getParameter("newPeriod");
            }

            List newAccounts = accountService.getAccountsByTimePeriod(newPeriod, false);
            request.setAttribute("newAccounts", newAccounts);
            
            if (request.getParameter("activePeriod") != null){
                String activePeriod = request.getParameter("activePeriod");
                request.setAttribute("activePeriod", activePeriod);
                List activeAccounts = accountService.getAccountsByTimePeriod(activePeriod, true);
                request.setAttribute("activeAccounts", activeAccounts);
            }
            
         getServletContext().getRequestDispatcher("/WEB-INF/admin/AdminReportUi.jsp").forward(request, response);           
        } catch (Exception ex) {            
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * Grabs the class information if a class has been selected 
     * then grabs the list of objects to be displayed to the page
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClassroomService classService = new ClassroomService();

        try {
            String action = request.getParameter("action");

            //grab the class to be viewec
            switch (action) {
                case "viewClass":
                    request.setAttribute("mode", "classList");
                    String viewClassId = request.getParameter("classId");
                    Classroom classToView = classService.getClassroom(viewClassId);
                    if (classToView == null) {
                        throw new NullPointerException();
                    }
                    request.setAttribute("classToView", classToView);
                    doGet(request, response);
                    return;
            }

            //get the list of all objects in the database using the right services
            AccountService accountService = new AccountService();
            ClassroomService classManager = new ClassroomService();
            RegistrationService registrationManager = new RegistrationService();
            PaymentDetailsService paymentManager = new PaymentDetailsService();
            ChildService childManager = new ChildService();

            List accountList = accountService.getAllAccounts();
            List classList = classManager.getAllClassrooms();
            List registrationList = registrationManager.getAllRegistrations();
            List paymentList = paymentManager.getAllPayments();
            List childList = childManager.getAllChildren();
            List<String> months = paymentManager.getDistinctMonths();

            request.setAttribute("accountList", accountList);
            request.setAttribute("childList", childList);
            request.setAttribute("classList", classList);
            request.setAttribute("registrationList", registrationList);
            request.setAttribute("paymentList", paymentList);
            request.setAttribute("months", months);
            
            getServletContext().getRequestDispatcher("/WEB-INF/TEST_manager.jsp").forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
