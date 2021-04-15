package filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import services.AccountService;

/**
 * Filtering for pages that can only be accessed by an authorized account
 *
 * @author Jean Lomibao
 */
public class AuthenticationFilter implements Filter {

    /**
     * Filters the request according to the type of account
     *
     * @param r The servlet request we are processing
     * @param rs The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest r, ServletResponse rs,
            FilterChain chain)
            throws IOException, ServletException {
        //check if there is a currently logged in user
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) rs;
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");

        //if there is no logged in user then redirect to login servlet
        //otherwise send request to the servlet
        if (accountId == null) {
            response.sendRedirect("/login?logout=logout");
            return;
        }

        //check if the user exists in the database
        try {
            AccountService accountService = new AccountService();
            Account account = accountService.getAccount(accountId);

            if (account == null) {
                Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, "Unauthorized access by user: {0}", accountId);
                response.sendRedirect("/login?logout=logout");
                return;
            }

        } catch (Exception ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, ex.getMessage());
            response.sendRedirect("/error?errorCode=D400");
        }

        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, t.getMessage());
            //if an error due to a filter an F400 error code is sent
            response.sendRedirect("/error?errorCode=F400");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

}
