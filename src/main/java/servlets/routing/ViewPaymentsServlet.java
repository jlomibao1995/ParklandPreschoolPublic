package servlets.routing;

import java.io.IOException;
import java.util.ArrayList;
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
import problemdomain.PaymentDetails;
import services.AccountService;
import services.ChildService;
import services.ClassroomService;
import services.PaymentDetailsService;

/**
 * Controller for viewing list of payments
 *
 * @author Jean Lomibao
 */
public class ViewPaymentsServlet extends HttpServlet {

    /**
     * {@inheritDoc} Grabs the list of payments and months then sends and loads
     * the right ViewPaymentsUi depending if user is a guardian or an admin
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            AccountService accountService = new AccountService();
            Account account = accountService.getAccount((String) session.getAttribute("accountId"));

            //get all payments and months with payments
            PaymentDetailsService paymentService = new PaymentDetailsService();
            List<String> months = paymentService.getDistinctMonths();
            request.setAttribute("months", months);

            //send page to guardian specific jsp if account is a guardian
            if (account.getAccountType() == 'G') {
                request.setAttribute("childList", account.getChildList());
                getServletContext().getRequestDispatcher("/WEB-INF/guardian/ViewPaymentsUi.jsp").forward(request, response);
                return;
            } else if (account.getAccountType() == 'S') {
                Logger.getLogger(ViewPaymentsServlet.class.getName()).log(Level.SEVERE, "Unauthorized access by user {0}", account.getAccountId());
                response.sendRedirect("login?logout=logout");
                return;
            }

            if (request.getAttribute("payments") == null) {
                List<PaymentDetails> payments = paymentService.getAll();
                request.setAttribute("payments", payments);
            }

            //get list of classes 
            ClassroomService classService = new ClassroomService();
            List<Classroom> classes = classService.getAllClassrooms();
            request.setAttribute("classes", classes);

            //get list of children
            ChildService childService = new ChildService();
            List<Child> children = childService.getAllChildren();
            request.setAttribute("childList", children);

            getServletContext().getRequestDispatcher("/WEB-INF/admin/ViewPaymentsUi.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewPaymentsServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * {@inheritDoc} Changes the list of payments depending on filter or updates
     * payment details on admin side
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            PaymentDetailsService paymentService = new PaymentDetailsService();
            List<PaymentDetails> payments = null;

            switch (action) {
                //finds payments for a specific class
                case "filterClass":
                    String classSelected = request.getParameter("classSelected");
                    payments = paymentService.getPaymentsByClass(classSelected);
                    break;
                //finds payments for a specific month
                case "filterMonth":
                    String month = request.getParameter("monthSelected");
                    payments = paymentService.getPaymentsByMonth(month);
                    request.setAttribute("selectedMonth", month);
                    break;
                //finds payments for a specific child
                case "filterChild":
                    String childSelected = request.getParameter("childSelected");
                    payments = paymentService.getPaymentsByChild(childSelected);
                    break;
                //process payment with another method
                case "paid":
                    String paymentMethod = request.getParameter("paymentMethod");
                    String paymentId = request.getParameter("paymentId");
                    paymentService.payWithOtherMethod(paymentId, paymentMethod);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Payment status has been saved");
            }

            //pass the list of payments to the request
            request.setAttribute("payments", payments);
            doGet(request, response);

        } catch (NumberFormatException ex) {
            //set the operation executed failed
            request.setAttribute("success", false);

            //set the right message according to the action
            if (action.equals("filterClass")) {
                request.setAttribute("message", "Please select a class");
            } else {
                request.setAttribute("message", "Please select a child");
            }

            doGet(request, response);
            //business rules that are not met throw the following exceptions
            //exceptions are caught and display the right message
        } catch (IllegalStateException ex) {
            //when user doesn't choose a month operation fails
            request.setAttribute("success", false);
            request.setAttribute("message", "Method description can't be more than 10 characters");
            doGet(request, response);
        } catch (NullPointerException ex) {
            //when user doesn't choose a month operation fails
            request.setAttribute("success", false);
            request.setAttribute("message", "Please select a month");
            doGet(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewPaymentsServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
