/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.routing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import services.AccountService;

/**
 * Controller for the MyAccountUi
 *
 * @author Jean Lomibao
 */
public class MyAccountServlet extends HttpServlet {

    /**
     * {@inheritDoc} Sends and loads the right MyAccountUi depending on type of
     * user
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //depending on the type of account forward the user to the right home page
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");

        try {

            AccountService accountService = new AccountService();
            Account account = accountService.getAccount(accountId);
            request.setAttribute("account", account);

            String mode = request.getParameter("mode");
            request.setAttribute("mode", mode);

            switch (account.getAccountType()) {
                case 'G':
                    getServletContext().getRequestDispatcher("/WEB-INF/guardian/MyAccountUi.jsp").forward(request, response);
                    break;
                case 'S':
                    getServletContext().getRequestDispatcher("/WEB-INF/staff/MyAccountUi.jsp").forward(request, response);
                    break;
                case 'A':
                    getServletContext().getRequestDispatcher("/WEB-INF/admin/MyAccountUi.jsp").forward(request, response);
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(MyAccountServlet.class.getName()).log(Level.SEVERE, "server error", ex);
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
     * {@inheritDoc} Performs update or deactivation on the account user is
     * currently logged into
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get information from inputs
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");

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

        AccountService accountService = new AccountService();
        String action = request.getParameter("action");

        try {
            switch (action) {
                //use the action parameter to determine the action the user is trying to do
                //use the AccountService to perform the account operations
                //when operation is executed successfully provide a message for success
                case "edit":
                    accountService.updateAccount(accountId, firstName, lastName, address, postalCode,
                            email, homePhone, workPhone, cellPhone, password, confirmPassword, "");
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Account changes saved");
                    break;
                case "deactivateAccount":
                    Account account = accountService.getAccount(accountId);
                    account.setAccountStatus(false);
                    accountService.updateAccount(account);
                    session.invalidate();
                    response.sendRedirect("home");
                    return;
            }

            //business rules that are not met throw an IllegalArgumentException
            //exceptions are caught and display the right message
        } catch (IllegalArgumentException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(MyAccountServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
