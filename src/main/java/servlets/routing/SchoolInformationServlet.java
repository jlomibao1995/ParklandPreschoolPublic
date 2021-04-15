/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.routing;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import services.AccountService;
import services.GmailService;

/**
 * Controller for the SchoolInformationUi
 *
 * @author Jean Lomibao
 */
public class SchoolInformationServlet extends HttpServlet {

    /**
     * {@inheritDoc} Sends and loads the right SchoolInformationUi depending on
     * type of user
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get the currently logged in user if any then send to the right jsp
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");
        
        //set tab to be active
        if (request.getAttribute("mode") == null){
            request.setAttribute("mode", "aboutus");
        }
        
        if (accountId == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/SchoolInformationUi.jsp").forward(request, response);
            return;
        }

        AccountService accountService = new AccountService();
        try {
            Account account = accountService.getAccount(accountId);

            switch (account.getAccountType()) {
                case 'G':
                    getServletContext().getRequestDispatcher("/WEB-INF/guardian/SchoolInformationUi.jsp").forward(request, response);
                    break;
                case 'A':
                    getServletContext().getRequestDispatcher("/WEB-INF/admin/SchoolInformationUi.jsp").forward(request, response);
                    break;
                case 'S':
                    getServletContext().getRequestDispatcher("/WEB-INF/staff/SchoolInformationUi.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(SchoolInformationServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String message = request.getParameter("message");

        try {
            //create a map for information placed on the email
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", firstName);
            tags.put("lastname", lastName);
            tags.put("email", email);
            tags.put("phone", phoneNumber);
            tags.put("message", message);
            
            String subject = "Preschool Inquiry";
            String template = getServletContext().getRealPath("/WEB-INF") + "/emailtemplates/contactus.html";
           
            GmailService.sendMail("parklandpreschoolteam@gmail.com", subject, template, tags);
            GmailService.sendMail(email, subject, template, tags);
            request.setAttribute("success", true);
            request.setAttribute("successMssg", "Message sent.");
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("success", false);
            request.setAttribute("successMssg", "Message could not be sent, try again.");
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("email", email);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("message", message);
        }
        request.setAttribute("mode", "contact");
        doGet(request, response);
    }

}
