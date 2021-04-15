package servlets.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.Account;
import problemdomain.Classroom;
import services.AccountService;
import services.ClassroomService;
import services.RegistrationService;

/**
 * Controller for handling CRUD operations for classes
 * @author Jean Lomibao
 * @author Nic Kelly
 */
public class ManageClassesServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Loads the ManageClassesUi with the class objects
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //will determine which tab is going to be active
        String mode = (String) request.getAttribute("mode");
        String paramMode = request.getParameter("mode");
        if (mode == null) {
            request.setAttribute("mode", "view");
        }

        if (paramMode != null) {
            request.setAttribute("mode", paramMode);
        }

        try {
            //grab the list of classes and class selected using the ClassroomService
            ClassroomService classService = new ClassroomService();
            request.setAttribute("classList", classService.getAllClassrooms());

            String classSelectedId = request.getParameter("classSelectedId");
            if (classSelectedId != null) {
                Classroom classSelected = classService.getClassroom(classSelectedId);
                request.setAttribute("classSelected", classSelected);
            }

            //grab list of accounts that staff for teacher list
            AccountService accountService = new AccountService();
            List<Account> teacherList = accountService.getAllAccounts();

            Iterator<Account> iter = teacherList.iterator();
            while (iter.hasNext()) {
                if (iter.next().getAccountType() == 'G') {
                    iter.remove();
                }
            }

            request.setAttribute("teacherList", teacherList);

            getServletContext().getRequestDispatcher("/WEB-INF/admin/ManageClassUi.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageClassesServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * Performs CRUD operations on Classrooms using the ClassroomService
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //initialize variables that will be used
        String description;
        String capacity;
        String accountId;
        String startDate;
        String endDate;
        String ageGroup;
        String year;
        String cost;
        String days;

        ClassroomService classService = new ClassroomService();

        String classId;
        Classroom c;

        String action = request.getParameter("action");

        try {
            switch (action) {
                //use the action parameter to determine the action the user is trying to do
                //use the ClassroomService to perform the classroom operations
                //when operation is executed successfully provide a message for success
                //then send to the right tab
                case "offerSpot":
                    request.setAttribute("mode", "waitlist");
                    RegistrationService registrationService = new RegistrationService();
                    String path = getServletContext().getRealPath("/WEB-INF");
                    String registrationId = request.getParameter("registrationId");
                    registrationService.offerSpotToWaitList(registrationId, path);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Parent has been offered the open spot through email");
                    break;

                case "viewClass":
                    request.setAttribute("mode", "view");
                    String viewClassId = request.getParameter("classId");
                    Classroom classToView = classService.getClassroom(viewClassId);
                    if (classToView == null) {
                        throw new NullPointerException();
                    }
                    request.setAttribute("classToView", classToView);
                    break;

                case "openEdit":
                    request.setAttribute("mode", "edit");
                    classId = request.getParameter("classId");
                    c = classService.getClassroom(classId);
                    if (c == null) {
                        throw new NullPointerException();
                    }

                    //format dates to display correctly
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                    request.setAttribute("startDate", format.format(c.getStartDate()));
                    request.setAttribute("endDate", format.format(c.getEndDate()));

                    request.setAttribute("classToEdit", c);
                    break;

                case "removeClass":
                    request.setAttribute("mode", "edit");
                    classId = request.getParameter("classId");

                    c = classService.getClassroom(classId);
                    if (c == null) {
                        throw new NullPointerException();
                    }

                    classService.deleteClassroom(c);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Class has been removed");
                    break;

                case "editClass":
                    request.setAttribute("mode", "edit");
                    classId = request.getParameter("classId");

                    c = classService.getClassroom(classId);
                    if (c == null) {
                        throw new NullPointerException();
                    }

                    description = request.getParameter("description");
                    capacity = request.getParameter("capacity");
                    accountId = request.getParameter("accountId");
                    startDate = request.getParameter("startDate");
                    endDate = request.getParameter("endDate");
                    ageGroup = request.getParameter("ageGroup");
                    year = request.getParameter("year");
                    cost = request.getParameter("costPerMonth");
                    days = request.getParameter("days");

                    classService.updateClassroom(description, ageGroup, days, capacity, accountId, startDate, endDate, year, cost, c);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Class has been updated");
                    break;

                case "addClass":
                    request.setAttribute("mode", "add");

                    description = request.getParameter("description");
                    capacity = request.getParameter("capacity");
                    accountId = request.getParameter("accountId");
                    startDate = request.getParameter("startDate");
                    endDate = request.getParameter("endDate");
                    ageGroup = request.getParameter("ageGroup");
                    year = request.getParameter("year");
                    cost = request.getParameter("costPerMonth");
                    days = request.getParameter("days");

                    classService.insertClassroom(description, days, capacity, accountId, startDate, endDate, year, ageGroup, cost);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Class has been added");
                    break;

                default:
                    break;
            }
            //business rules that are not met throw an IllegalArgumentException
            //exceptions are caught and display the right message 
        } catch (IllegalArgumentException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());
        } catch (NullPointerException ex) {
            request.setAttribute("success", false);
            request.setAttribute("message", "Couldn't get class, please reload.");
        } catch (Exception ex) {
            Logger.getLogger(ManageClassesServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
