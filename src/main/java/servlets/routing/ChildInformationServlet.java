package servlets.routing;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import problemdomain.Child;
import problemdomain.Classroom;
import services.AccountService;
import services.ChildService;
import services.ClassroomService;

/**
 * Controller for displaying child information in the ChildInformationUi
 * @author Jean Lomibao
 */
public class ChildInformationServlet extends HttpServlet {

    /**
     * {@inheritDoc}
     * Grabs the child based on the child id using the ChildServices class
     * then loads the ChildInformationUi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            //get the child object
            String childId = request.getParameter("childId");
            ChildService childService = new ChildService();
            Child child = childService.getChild(childId);

            //grab list of classes the child can be moved to
            ClassroomService classService = new ClassroomService();
            List<Classroom> classes = classService.getClassroomsForChild(child);

            request.setAttribute("classes", classes);
            request.setAttribute("child", child);

            HttpSession session = request.getSession();
            String accountId = (String) session.getAttribute("accountId");

            AccountService accountService = new AccountService();
            Account account = accountService.getAccount(accountId);

            //depending on account type send to the right jsp
            switch (account.getAccountType()) {
                case 'S':
                    getServletContext().getRequestDispatcher("/WEB-INF/staff/ChildInformationUi.jsp").forward(request, response);
                    break;
                case 'A':
                    getServletContext().getRequestDispatcher("/WEB-INF/admin/ChildInformationUi.jsp").forward(request, response);
                    break;
                case 'G':
                    Logger.getLogger(ChildInformationServlet.class.getName()).log(Level.SEVERE, "Unauthorized access by user {0}", account.getAccountId());
                    response.sendRedirect("login?logout=logout");
            }
        } catch (Exception ex) {
            Logger.getLogger(ChildInformationServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * {@inheritDoc}
     * Moves the child to a different class if valid
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "move":
                    String classId = request.getParameter("class");
                    String registrationId = request.getParameter("registration");
                    ClassroomService classService = new ClassroomService();
                    classService.moveChildToClass(registrationId, classId);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Child has been moved to a different class");
                    break;
            }

            //business rules that are not met throw an IllegalArgumentException
            //exceptions are caught and display the right message 
        } catch (IllegalArgumentException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("success", false);
        } catch (Exception ex) {
            Logger.getLogger(ChildInformationServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U500";
            if (ex.getMessage().equals("Database error")) {
                errorCode = "D400";
            }

            response.sendRedirect("/error?errorCode=" + errorCode);
            return;
        }

        doGet(request, response);

    }
}
