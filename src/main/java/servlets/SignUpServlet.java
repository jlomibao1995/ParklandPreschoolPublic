package servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

/**
 * Controller for handling account sign ups
 *
 * @author Jean Lomibao, Ethan Foster
 *
 */
public class SignUpServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Loads the SignUpUi and activates an account that has received the
     * activation link when sign up has been successful
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //if user is logged in redirect to home
        HttpSession session = request.getSession();
        if (session.getAttribute("accountId") != null){
            response.sendRedirect("home");
            return;
        }
        
        String uuid = request.getParameter("uuid");

        try {
            //if uuid is provided then user is trying to activate newly created account
            //activate account and provide message that account has been activate
            //then redirect user to the login page
            if (uuid != null) {
                AccountService accountService = new AccountService();
                accountService.activateUser(uuid);
                request.setAttribute("success", true);
                request.setAttribute("message", "Account has been successfully activated");
                request.setAttribute("mode", "login");
                getServletContext().getRequestDispatcher("/WEB-INF/LoginUi.jsp").forward(request, response);
                return;
            }

            getServletContext().getRequestDispatcher("/WEB-INF/SignUpUi.jsp").forward(request, response);
        } catch (Exception ex) {
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U500";
            if (ex.getMessage().equals("Database error")) {
                errorCode = "D400";
            }

            Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            response.sendRedirect("/error?errorCode=" + errorCode);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //get information from inputs
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String homePhone = request.getParameter("homePhone");
        String cellPhone = request.getParameter("cellPhone");
        String workPhone = request.getParameter("workPhone");
        String postalCode = request.getParameter("postalCode");

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            //create account using the service 
            AccountService accountService = new AccountService();
            String path = getServletContext().getRealPath("/WEB-INF");
            String url = request.getRequestURL().toString();
            accountService.registerNewAccount(firstName, lastName, address, postalCode,
                    email, homePhone, workPhone, cellPhone, password, confirmPassword,
                    path, url);

            //inform front end that the account was created
            request.setAttribute("success", true);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());

            //reenter user input
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("address", address);
            request.setAttribute("email", email);
            request.setAttribute("homePhone", homePhone);
            request.setAttribute("cellPhone", cellPhone);
            request.setAttribute("workPhone", workPhone);
            request.setAttribute("postalCode", postalCode);
        } catch (Exception ex) {
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            String errorCode = "U500";
            if (ex.getMessage().equals("Database error")) {
                errorCode = "D400";
            }

            Logger.getLogger(SignUpServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            response.sendRedirect("/error?errorCode=" + errorCode);
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/SignUpUi.jsp").forward(request, response);
    }

}
