package servlets.staff;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import problemdomain.Classroom;
import services.ClassroomService;

/**
 * Controller for ViewingClassesUi for the Staff 
 * @author Jean Lomibao
 */
public class ViewClassesServlet extends HttpServlet {

    /**
     * {@inheritDoc}
     * Loads class list and the ViewClassUi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        try {
            ClassroomService classService = new ClassroomService();
            request.setAttribute("classList", classService.getAllClassrooms());
            
            getServletContext().getRequestDispatcher("/WEB-INF/staff/ViewClassUi.jsp").forward(request, response);
        } catch (Exception ex) {
            //if an error occurs somewhere that is not handled the user is redirected to 
            //the error page
            //database errors are denoted with error code D#400
            //unexpected errors are denoted with error code U#500
            if (ex.getMessage().equals("Database error")) {
                request.setAttribute("errorCode", "D400");
            } else {
                request.setAttribute("errorCode", "U500");
            }
            
            Logger.getLogger(ViewClassesServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
            response.sendRedirect("error");
        }
    }

    /**
     * {@inheritDoc}
     * Sends the class information based on class id selected by the user
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ClassroomService classService = new ClassroomService();
            String viewClassId = request.getParameter("classId");
            Classroom classToView = classService.getClassroom(viewClassId);
            if (classToView == null) {
                throw new NullPointerException("Choose a class to be viewed");
            }
            request.setAttribute("classToView", classToView);
            request.setAttribute("classList", classService.getAllClassrooms());
            getServletContext().getRequestDispatcher("/WEB-INF/staff/ViewClassUi.jsp").forward(request, response);
        } catch (NullPointerException ex) {
            //when user doesn't choose a month operation fails
            request.setAttribute("success", false);
            request.setAttribute("message", ex.getMessage());
            doGet(request, response);
        } catch (Exception ex) {           
            Logger.getLogger(ViewClassesServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
