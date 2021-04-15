/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Filters access to guardian servlets
 *
 * @author Nic
 */
public class GuardianPanelFilter implements Filter {

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

        //grab the currently logged in user's ID from session
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) rs;
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");

        //if account with id is a guardian let the request go through 
        //other wise redirect to login and log the unauthorized access
        AccountService as = new AccountService();
        try {
            Account account = as.getAccount(accountId);
            if (account.getAccountType() == 'G') {
                chain.doFilter(request, response);
            } else {
                Logger.getLogger(AdminPanelFilter.class.getName()).log(Level.SEVERE, "Unauthorized access by {0}", accountId);
                response.sendRedirect("/login?logout=logout");
                return;
            }
        } catch (Throwable t) {
            Logger.getLogger(AdminPanelFilter.class.getName()).log(Level.SEVERE, t.getMessage());
            //if error due to a filter an F400 error code is sent
            response.sendRedirect("/error?errorCode=F400");
        }
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
    }

}
