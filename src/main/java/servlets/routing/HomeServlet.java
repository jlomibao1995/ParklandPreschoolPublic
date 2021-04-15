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
 * Controller for the HomePageUi
 * @author Jean Lomibao
 */
public class HomeServlet extends HttpServlet {

    /**
     * {@inheritDoc}
     * Sends and loads the right HomePageUi according to the user type 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //depending on the type of account forward the user to the right home page
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");

        try {
            if (accountId == null) {
                getServletContext().getRequestDispatcher("/WEB-INF/HomePageUi.jsp").forward(request, response);
            } else {
                AccountService accountService = new AccountService();
                Account account = accountService.getAccount(accountId);
                
                String name = account.getAccountFirstName();

                switch (account.getAccountType()) {
                    case 'G':
                        request.setAttribute("name", name);
                        getServletContext().getRequestDispatcher("/WEB-INF/guardian/HomePageUi.jsp").forward(request, response);
                        break;
                    case 'S':
                        request.setAttribute("name", name);
                        getServletContext().getRequestDispatcher("/WEB-INF/staff/HomePageUi.jsp").forward(request, response);
                        break;
                    case 'A':
                        request.setAttribute("name", name);
                        getServletContext().getRequestDispatcher("/WEB-INF/admin/HomePageUi.jsp").forward(request, response);
                        break;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
