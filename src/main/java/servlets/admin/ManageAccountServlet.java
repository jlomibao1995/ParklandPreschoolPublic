package servlets.admin;

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
import services.AccountService;

/**
 * Controller for handling CRUD operations for accounts
 *
 * @author Jean Lomibao
 * @author Nic Kelly
 */
public class ManageAccountServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Loads the ManageAccountsUi with the accounts objects
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //get the account lists and account information for the account
            //selected by user and pass them to the request
            AccountService accountService = new AccountService();
            String accountSelected = request.getParameter("accountSelected");

            String query = request.getParameter("query");

            List<Account> accountList = accountService.getAccounts(query);
            request.setAttribute("accountList", accountList);

            //determine what mode the user is in to load the right tab 
            if (request.getParameter("mode") != null) {
                request.setAttribute("mode", request.getParameter("mode"));
            }

            //if an account has been selected then the user is in edit mode
            if (accountSelected != null) {
                request.setAttribute("account", accountService.getAccount(accountSelected));
                request.setAttribute("mode", "edit");
            } else if (query != null) {
                request.setAttribute("mode", "edit");
            }
            //send to the right JSP
            getServletContext().getRequestDispatcher("/WEB-INF/admin/ManageAccountsUi.jsp").forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ManageAccountServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * Performs CRUD operations on accounts using the AccountService
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //grab the currently logged in user
        HttpSession session = request.getSession();
        String currentAccount = (String) session.getAttribute("accountId");

        String action = request.getParameter("action");

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

        //initialize variables that will be used several times
        String accountId = "";
        String accountType;
        Account account = null;
        AccountService accountService = new AccountService();

        try {
            request.setAttribute("accountList", accountService.getAccounts(""));

            switch (action) {
                //use the action parameter to determine the action the user is trying to do
                //use the AccountService to perform the account operations
                //when operation is executed successfully provide a message for success
                case "addAccount":
                    request.setAttribute("mode", "add");
                    accountType = request.getParameter("accountType");
                    String confirmPassword = request.getParameter("confirmPassword");
                    accountService.createAccount(firstName, lastName, address, postalCode,
                            email, homePhone, cellPhone, workPhone, password, confirmPassword, accountType);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Account has been added");
                    break;
                case "activate":
                    request.setAttribute("mode", "inactive");
                    accountId = request.getParameter("accountId");
                    account = accountService.getAccount(accountId);
                    account.setAccountStatus(true);
                    accountService.updateAccount(account);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Account has been activated");
                    break;
                case "deactivate":
                    request.setAttribute("mode", "active");
                    accountId = request.getParameter("accountId");

                    if (accountId.equals(currentAccount)) {
                        throw new IllegalArgumentException("Unable to deactivate the current account");
                    }

                    account = accountService.getAccount(accountId);
                    account.setAccountStatus(false);
                    accountService.updateAccount(account);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Account has been deactivated");
                    break;
                case "delete":
                    request.setAttribute("mode", "inactive");
                    accountId = request.getParameter("accountId");

                    if (accountId.equals(currentAccount)) {
                        throw new IllegalArgumentException("Unable to delete the current account");
                    }

                    account = accountService.getAccount(accountId);
                    accountService.removeAccount(account);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Account has been deleted");
                    break;
                case "edit":
                    request.setAttribute("mode", "edit");
                    accountId = request.getParameter("accountSelected");
                    account = accountService.getAccount(accountId);
                    accountType = request.getParameter("accountType");
                    accountService.updateAccount(accountId, firstName, lastName,
                            address, postalCode, email, homePhone, workPhone,
                            cellPhone, password, password, accountType);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Account changes saved");
                    request.setAttribute("account", account);
                    break;
                case "selectInactiveAccount":
                    request.setAttribute("mode", "inactive");
                    accountId = request.getParameter("inactiveSelected");
                    request.setAttribute("inactiveAccount", accountId);
                    break;
                case "emailPopup":
//                    accountId = request.getParameter("accountId");
//                    account = accountService.getAccount(accountId);
//                    String accountEmail = account.getEmail();
//                    request.setAttribute("accountEmail", accountEmail);
//                    getServletContext().getRequestDispatcher("/WEB-INF/admin/SendMessage.jsp").forward(request, response);
                default:
                    break;
            }

            //business rules that are not met throw an IllegalArgumentException
            //exceptions are caught and display the right message 
        } catch (IllegalArgumentException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());

            if (request.getAttribute("mode").equals("edit")) {
                request.setAttribute("account", account);
            } else {
                //reenter user input
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("address", address);
                request.setAttribute("email", email);
                request.setAttribute("homePhone", homePhone);
                request.setAttribute("cellPhone", cellPhone);
                request.setAttribute("workPhone", workPhone);
                request.setAttribute("postalCode", postalCode);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManageAccountServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
        
        //getServletContext().getRequestDispatcher("/WEB-INF/admin/ManageAccountsUi.jsp").forward(request, response);
        doGet(request, response);

    }

}
