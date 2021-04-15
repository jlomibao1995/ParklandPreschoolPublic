package servlets.guardian;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import problemdomain.Account;
import problemdomain.AuthorizedPickup;
import problemdomain.Child;
import problemdomain.EmergencyContact;
import services.AccountService;
import services.ChildService;
import services.RegistrationService;

/**
 * Controller for handling CRUD operations for children 
 * @author Jean Lomibao
 */
public class ManageChildrenServlet extends HttpServlet {

    /**
     * {@inheritDoc }
     * Grabs all the children belonging to the account and the child selected
     * by the user to be viewed then loads the ManageChildrenUi
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("accountId");
        String mode = request.getParameter("mode");

        try {
            AccountService accountService = new AccountService();
            Account account = accountService.getAccount(accountId);
            request.setAttribute("children", account.getChildList());

            Child childSelected = null;
            //find child in account list
            String childId = request.getParameter("childId");
            for (Child child : account.getChildList()) {
                if (child.getChildId().toString().equals(childId)) {
                    childSelected = child;
                    request.setAttribute("child", child);
                }
            }

            //form will autofill add form if a child already exists
            //direct to the right jsp depending if user is adding or updaing a child
            if (mode != null && mode.equals("add")) {
                Child newChild = new Child();
                newChild.setAccount(account);

                if (!account.getChildList().isEmpty()) {
                    newChild.setEmergencyContactList(account.getChildList().get(0).getEmergencyContactList());
                    newChild.setAuthorizedPickupList(account.getChildList().get(0).getAuthorizedPickupList());
                }

                request.setAttribute("child", newChild);
                getServletContext().getRequestDispatcher("/WEB-INF/guardian/AddChildUi.jsp").forward(request, response);
                return;
            } else if (mode != null && mode.equals("update")) {
                request.setAttribute("month",new SimpleDateFormat("MM").format(childSelected.getChildBirthdate()));
                getServletContext().getRequestDispatcher("/WEB-INF/guardian/UpdateChildUi.jsp").forward(request, response);
                return;
            }

            getServletContext().getRequestDispatcher("/WEB-INF/guardian/ManageChildUi.jsp").forward(request, response);
        } catch (Exception ex) {           
            Logger.getLogger(ManageChildrenServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
     * Performs the CRUD operations for the children of the account using the 
     * ChildService class
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //figure if child is being added, withdrawn, deleted or updated
        String action = request.getParameter("action");

        //grab all information for the child
        String firstName = request.getParameter("firstName");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String allergies = request.getParameter("allergies");
        String doctor = request.getParameter("doctorName");
        String medicalPhoneNum = request.getParameter("medicalPhoneNumber");
        String lastName = request.getParameter("lastName");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String year = request.getParameter("year");
        String postalCode = request.getParameter("postalCode");
        String healthCareNum = request.getParameter("healthCareNum");
        String clinic = request.getParameter("clinic");
        String immunizations = request.getParameter("shots");
        String hadChickenPox = request.getParameter("pox");
        String medications = request.getParameter("medications");
        String medicalConditions = request.getParameter("medicalConditions");

        //grab the account's relation to child
        String accountRelation = request.getParameter("accountRelation");

        //grab second guardian's information
        String guardian2FirstName = request.getParameter("guardian2FirstName");
        String guardian2Address = request.getParameter("guardian2Address");
        String guardian2HomePhone = request.getParameter("guardian2HomePhone");
        String guardian2WorkPhone = request.getParameter("guardian2WorkPhone");
        String guardian2LastName = request.getParameter("guardian2LastName");
        String guardian2Relation = request.getParameter("guardian2Relation");
        String guardian2CellPhone = request.getParameter("guardian2CellPhone");

        //grab emergency contact's information
        String emergencyFirstName = request.getParameter("emergencyFirstName");
        String emergencyAddress = request.getParameter("emergencyAddress");
        String emergencyHomePhone = request.getParameter("emergencyHomePhone");
        String emergencyLastName = request.getParameter("emergencyLastName");
        String emergencyRelation = request.getParameter("emergencyRelation");
        String emergencyCellPhone = request.getParameter("emergencyCellPhone");

        //grab information for other caregiver
        String otherFirstName = request.getParameter("otherFirstName");
        String otherAddress = request.getParameter("otherAddress");
        String otherLastName = request.getParameter("otherLastName");
        String otherPhone = request.getParameter("otherPhone");

        //grab authorized pickup's information
        String authorized1FirstName = request.getParameter("authorized1FirstName");
        String authorized1LastName = request.getParameter("authorized1LastName");
        String authorized2FirstName = request.getParameter("authorized2FirstName");
        String authorized2LastName = request.getParameter("authorized2LastName");

        Date birthdate = null;

        try {          
            //get account info
            HttpSession session = request.getSession();
            String accountId = (String) session.getAttribute("accountId");

            ChildService childService = new ChildService();

            //if withrawing or deleting a child perform the operation here then 
            //get the information to be displayed using the doGet method
            if (action.equals("withdraw")) {
                RegistrationService registrationService = new RegistrationService();
                String registrationId = request.getParameter("registrationId");
                String path = getServletContext().getRealPath("/WEB-INF");
                registrationService.withdrawChildRegistration(registrationId, path);
                request.setAttribute("success", true);
                request.setAttribute("message", "Child has been withdrawn");
                doGet(request, response);
                return;
            } else if (action.equals("delete")) {
                String childId = request.getParameter("childId");
                childService.deleteChildForGuardian(childId, accountId);
                request.setAttribute("success", true);
                request.setAttribute("message", "Child has been deleted from account");
                doGet(request, response);
                return;
            }
            
            //when performing an update or creating a new child 
            //create the instance of the information needed for the child using the 
            //child service, if business rules are violated an IllegalArgumentException is thrown
            birthdate = new SimpleDateFormat("MM/d/yyyy").parse(month + "/" + day + "/" + year);

            //create child instance with the information provided
            Child child = childService.getChildInstance(gender, firstName, lastName,
                    address, postalCode, doctor, clinic, immunizations, medications,
                    hadChickenPox, medicalPhoneNum, healthCareNum, allergies, medicalConditions,
                    month, day, year);

            //create instance for the second guardian's information
            EmergencyContact guardian2 = childService.getGuardian2Instance(guardian2FirstName,
                    guardian2LastName, guardian2Address, guardian2HomePhone, guardian2WorkPhone,
                    guardian2CellPhone, guardian2Relation);

            //create instance with emergency contact's information
            EmergencyContact emergencyContact = childService.getEmergencyContactInstance(emergencyFirstName,
                    emergencyLastName, emergencyAddress, emergencyHomePhone, emergencyCellPhone, emergencyRelation);

            //create instance with the caregiver's information
            EmergencyContact otherCaregiver = childService.getOtherCaregiverInstance(otherFirstName,
                    otherLastName, otherAddress, otherPhone);

            //create instance with authorized pickup information
            AuthorizedPickup authorized1 = childService.getAuthorizedPickUpInstance(authorized1FirstName, authorized1LastName);
            AuthorizedPickup authorized2 = childService.getAuthorizedPickUpInstance(authorized2FirstName, authorized2LastName);

            //perform the right CRUD operation depending on what the user wants using the action parameter
            //when operation is executed successfully provide a message for success
            switch (action) {
                case "add":
                    childService.addChild(accountId, child, accountRelation, guardian2, emergencyContact, otherCaregiver, authorized1, authorized2);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Child added to account");
                    break;
                case "save":
                    child.setChildId(Integer.parseInt(request.getParameter("childId")));
                    childService.updateChildInformation(accountId, child, accountRelation, guardian2, emergencyContact, otherCaregiver, authorized1, authorized2);
                    request.setAttribute("success", true);
                    request.setAttribute("message", "Child information has been updated");
                    break;
            }

            doGet(request, response);
            
            //when business rules are violated the following exceptions are thrown
        } catch (ParseException ex) {
            request.setAttribute("message", "Date entered invalid");
            request.setAttribute("success", false);
            doGet(request, response);
        } catch (NullPointerException ex) {
            request.setAttribute("message", "User missing a required input");
            request.setAttribute("success", false);
            doGet(request, response);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("message", ex.getMessage());
            request.setAttribute("success", false);

            //refill information that was entered
            if (action.equals("add")) {

                //save information in a child object to be passed if something goes wrong, data isn't lost
                Child errorChild = new Child(0, birthdate, gender.charAt(0), firstName, lastName,
                        address, " ", " ", postalCode, doctor, clinic, Boolean.valueOf(immunizations),
                        Boolean.valueOf(hadChickenPox), medicalPhoneNum, healthCareNum, allergies);
                errorChild.setMedications(Boolean.valueOf(medications));
                errorChild.setEmergencyContactList(new ArrayList<EmergencyContact>());
                errorChild.getEmergencyContactList().add(new EmergencyContact(0, guardian2Relation,
                        guardian2FirstName, guardian2Address, guardian2LastName, guardian2HomePhone, guardian2WorkPhone, guardian2CellPhone));
                errorChild.getEmergencyContactList().add(new EmergencyContact(0, " ", emergencyFirstName,
                        emergencyLastName, emergencyAddress, emergencyHomePhone, " ", emergencyCellPhone));
                errorChild.getEmergencyContactList().add(new EmergencyContact(0, " ", otherFirstName,
                        otherLastName, otherAddress, otherPhone, " ", " "));
                errorChild.setAuthorizedPickupList(new ArrayList<AuthorizedPickup>());
                errorChild.getAuthorizedPickupList().add(new AuthorizedPickup(0, authorized1FirstName, authorized1LastName, ""));
                errorChild.getAuthorizedPickupList().add(new AuthorizedPickup(0, authorized2FirstName, authorized2LastName, ""));
                errorChild.setAccount(new Account(request.getParameter("accountName"), accountRelation));

                request.setAttribute("month", month);
                request.setAttribute("child", errorChild);

                //route back to the add page
                getServletContext().getRequestDispatcher("/WEB-INF/guardian/AddChildUi.jsp").forward(request, response);
                return;
            }

            doGet(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ManageChildrenServlet.class.getName()).log(Level.SEVERE, "Server error", ex);
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
