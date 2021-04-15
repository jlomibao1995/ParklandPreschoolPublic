package servlets.routing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import services.AccountService;

/**
 * Controller for the LoginUi and login operations
 *
 * @author Jean Lomibao
 */
public class LoginServlet extends HttpServlet {

    /**
     * {@inheritDoc} Loads the LoginUi and sends which view to load depending on
     * the operations the user wants to perform
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        //find out if user wants to login or logout
        String logout = request.getParameter("logout");
        //remove everything in the session if user wants to logout
        if (logout != null && logout.equals("logout")) {
            session.invalidate();
        }

        //redirect to home page if user already logged in
        if (logout == null && session.getAttribute("accountId") != null) {
            response.sendRedirect("home");
            return;
        }

        //find if user wants to login, forgot their password or resetting their password
        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");
        String mode = "login";

        //display the right forms depending on what the user is trying to do
        
        //go to reset form if user is trying to reset password
        if (action != null && action.equals("forgot")) {
            mode = "forgot";
        }

        //if uuid is not null then show reset password form 
        if (uuid != null) {
            mode = "reset";
            request.setAttribute("uuid", uuid);
        }

        request.setAttribute("mode", mode);

        getServletContext().getRequestDispatcher("/WEB-INF/LoginUi.jsp").forward(request, response);
    }

    /**
     * {@inheritDoc} Authenticates user's login credentials or sends the user a
     * link to reset password using the AccountService class
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //use the AccountServices to authenticate user and get an Account object
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String uuid = "";

        //keep track of what mode we want the user to be in
        String mode = request.getParameter("mode");

        //use the account services
        AccountService accountService = new AccountService();
        Account account;
        try {

            switch (action) {
                //if user wants to login validate the information provided
                case "login":
                    account = accountService.login(email, password);
                    //if the email and password are correct accountId is stored for the session
                    if (account != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("accountId", account.getAccountId().toString());
                        //forward to the home page 
                        response.sendRedirect("home");
                        return;
                    } else {
                        //if login failed go back to the login page
                        request.setAttribute("email", email);
                        request.setAttribute("password", password);
                        request.setAttribute("mode", "login");
                        request.setAttribute("success", false);
                        request.setAttribute("message", "Email or password entered incorrect");
                    }
                    break;
                case "forgotPassword":
                    //grab the user's email if they want to request a new password
                    request.setAttribute("mode", "forgot");
                    String path = getServletContext().getRealPath("/WEB-INF");
                    //send the user back to the same url
                    String url = request.getRequestURL().toString();
                    //send reset link to email entered 
                    accountService.sendResetPasswordLink(email, url, path);

                    //send user back to the login page
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Check your email for link to reset your password");
                    break;
                case "resetPassword":
                    String confirmPassword = request.getParameter("confirmPassword");
                    uuid = request.getParameter("uuid");
                    accountService.resetPassword(uuid, password, confirmPassword);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Password has been changed");
                    request.setAttribute("mode", "login");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/LoginUi.jsp").forward(request, response);
        } catch (NoResultException ex) {
            //if login failed go back to the login page
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("mode", mode);
            request.setAttribute("success", false);
            request.setAttribute("message", "Account with that email does not exist");
            getServletContext().getRequestDispatcher("/WEB-INF/LoginUi.jsp").forward(request, response);
        } catch (IllegalArgumentException ex) {
            //if login failed go back to the login page
            request.setAttribute("mode", mode);
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());

            if (uuid != null) {
                request.setAttribute("uuid", uuid);
            }

            getServletContext().getRequestDispatcher("/WEB-INF/LoginUi.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
