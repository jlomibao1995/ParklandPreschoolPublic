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
 * Controller for the ErrorUi
 * @author Jean Lomibao
 */
public class ErrorServlet extends HttpServlet {

    /**
     * {@inheritDoc}
     * Sends and loads the right ErrorUi according to the user type 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //grab error code sent from servlets
        String errorCode = request.getParameter("errorCode");
        if (errorCode != null){
            request.setAttribute("errorCode", errorCode);
        }
        
        //depending on user logged in and the type of account send the user to the
        //right Ui
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");
        if (accountId == null){
            getServletContext().getRequestDispatcher("/WEB-INF/ErrorUi.jsp").forward(request, response);
            return;
        }
        
        AccountService accountService = new AccountService();
        try {
            Account account = accountService.getAccount(accountId);
            
            switch (account.getAccountType()){
                case 'G':
                    getServletContext().getRequestDispatcher("/WEB-INF/guardian/ErrorUi.jsp").forward(request, response);
                    break;
                case 'A':
                    getServletContext().getRequestDispatcher("/WEB-INF/admin/ErrorUi.jsp").forward(request, response);
                    break;
                case 'S':
                   getServletContext().getRequestDispatcher("/WEB-INF/staff/ErrorUi.jsp").forward(request, response); 
                   break;
            }
        } catch (Exception ex) {
            Logger.getLogger(ErrorServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            getServletContext().getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
