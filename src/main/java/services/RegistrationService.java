package services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import persistence.ChildDBBroker;
import persistence.ClassroomDBBroker;
import persistence.PaymentDBBroker;
import persistence.RegistrationDBBroker;
import problemdomain.Account;
import problemdomain.Child;
import problemdomain.Classroom;
import problemdomain.PaymentDetails;
import problemdomain.Registration;

/**
 * Accesses preschool database and manages the Registrations table
 *
 * @author Jean Lomibao
 */
public class RegistrationService {

    /**
     * Adds necessary child information and registers child to a class
     *
     * @param childId child to be registered
     * @param classId class to be registered to
     * @param path path for the email template
     * @param safety safety policy signature
     * @param outdoor outdoor policy signature
     * @param discipline discipline policy signature
     * @param sick sick policy signature
     * @return message if the registration was successfully added to the
     * database
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public String registerChild(String childId, String classId, String path,
            String safety, String outdoor, String discipline, String sick) throws Exception {
        //get class to be registered to as well as the waitlist class
        ClassroomDBBroker classDB = new ClassroomDBBroker();
        Classroom classroom = classDB.getClassroom(Integer.parseInt(classId));

        //get child object that is being registered
        ChildDBBroker childDB = new ChildDBBroker();
        Child child = childDB.getChild(Integer.parseInt(childId));
        String guardianName = child.getAccount().getAccountFirstName().toUpperCase()
                + " " + child.getAccount().getAccountLastName().toUpperCase();

        //check if guardian name matches with the signature
        if (!sick.equals(guardianName) || !safety.equals(guardianName)
                || !outdoor.equals(guardianName) || !discipline.equals(guardianName)) {
            throw new IllegalArgumentException("Signature does not match account name");
        }

        //check if child is being registered for the right class
        if (child.getAge() != classroom.getAgeGroup()) {
            throw new IllegalArgumentException("Child can't be registered for that class");
        }

        //check if child has already been registered for class
        for (Registration registration : child.getRegistrationList()) {
            if (Objects.equals(registration.getClassroom().getClassroomId(), classroom.getClassroomId())) {
                throw new IllegalArgumentException("Child already has a registration for that class");
            }
        }

        RegistrationDBBroker registrationDB = new RegistrationDBBroker();

        //create new registration, set registration status to registration fee not paid
        Registration registration = new Registration(0, true, 'N', false);
        registration.setOutdoorSigDateStamp(new Date());
        registration.setSafetySigDateStamp(new Date());
        registration.setSickSigDateStamp(new Date());
        registration.setDisciplineSigDateStamp(new Date());
        registration.setChild(child);
        registration.setRegistrationDate(new Date());
        registration.setClassroom(classroom);

        //add registration to child and classroom 
        child.getRegistrationList().add(registration);
        classroom.getRegistrationList().add(registration);

        //if the class is full add child to waitlist
        if (classroom.getCapacity() > classroom.getEnrolled()) {
            //update everything in the database
            registrationDB.addChildRegistration(child, classroom, registration);

            //process payment
            PaymentDetails payment = makeRegistrationFee(registration);
            registration.getPaymentDetailsList().add(payment);

            return payment.getPaymentId().toString();

        } else {
            registration.setRegistrationStatus('W');
            registrationDB.addChildRegistration(child, classroom, registration);

            Account account = child.getAccount();

            //prepare the email templates 
            String subject = "Child Wait Listed";
            String template = path + "/emailtemplates/waitlist.html";

            //send email to account that child has been wait listed
            //format days, times, and dates
            SimpleDateFormat date = new SimpleDateFormat("MMM dd yyyy");
            SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
            String dates = date.format(classroom.getStartDate()) + " - " + date.format(classroom.getEndDate());
            String times = time.format(classroom.getStartDate()) + " - " + time.format(classroom.getEndDate());

            String days = "Tuesday/Thursday";
            if (classroom.getDays().equals("MWF")) {
                days = "Monday/Wednesday/Friday";
            }

            //create a map for information place on the email
            HashMap<String, String> tags = new HashMap<>();
            tags.put("parent", account.getAccountFirstName() + " " + account.getAccountLastName());
            tags.put("child", child.getChildFirstName() + " " + child.getChildLastName());
            tags.put("class", classroom.getDescription());
            tags.put("dates", dates);
            tags.put("time", times);
            tags.put("days", days);

            //send email
            GmailService.sendMail(account.getEmail(), subject, template, tags);

            return "W" + registration.getRegistrationId();
        }
    }

    /**
     * Creates a record of payment details and returns it
     *
     * @param registration registration that is being paid for
     * @return payment information
     * @throws Exception thrown when a database error occurs
     */
    private PaymentDetails makeRegistrationFee(Registration registration) throws Exception {
        //make a payment 
        PaymentDetails payment = new PaymentDetails();

        //enter the right amount and description for a registration fee
        payment.setPaymentSubtotal(45);
        payment.setPaymentTotal(45);
        payment.setPaymentDescription("registration fee");
        payment.setPaymentDate(new Date());

        //format month
        String paymentMonth = (LocalDate.now()).getMonth().toString();
        paymentMonth = paymentMonth.substring(0, 1) + paymentMonth.substring(1).toLowerCase();
        paymentMonth += " " + LocalDate.now().getYear();

        //set the rest of the information
        payment.setPaymentMonth(paymentMonth);
        payment.setPaymentStatus('N');
        payment.setPaymentMethod("Not paid");
        registration.getPaymentDetailsList().add(payment);
        payment.setRegistration(registration);
        PaymentDBBroker paymentDB = new PaymentDBBroker();

        //save information to database
        payment = paymentDB.insertPayment(payment);
        return payment;
    }

    /**
     * Registration is accepted by the admin and status is updated Sends email
     * to parent informing that registration has been approved by admin
     *
     * @param registrationId unique id for the registration of the child
     * @throws java.lang.Exception thrown if an error with the database is
     * encountered
     */
    public void acceptRegistration(String registrationId) throws Exception {
        //get the registraiton from the registration id
        Registration registration = getRegistration(registrationId);

        //update status
        registration.setRegistrationStatus('R');
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        registrationDB.updateRegistration(registration);
    }

    /**
     * Registration status is updated to denied by admin
     *
     * @param registrationId registration for the child
     * @throws java.lang.Exception thrown when an error occurs
     */
    public void denyRegistration(String registrationId) throws Exception {
        //get the registraiton from the registration id
        Registration registration = getRegistration(registrationId);

        //update registration status 
        registration.setRegistrationStatus('D');
        registration.setRegistrationActive(false);
        int enrolled = registration.getClassroom().getEnrolled();
        registration.getClassroom().getRegistrationList().remove(registration);
        registration.getClassroom().setEnrolled(enrolled - 1);

        //update registration in the database
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        registrationDB.updateRegistration(registration);
    }

    /**
     * Deactivate registration and updates the database
     *
     * @param registrationId registration to be deactivated
     * @throws java.lang.Exception thrown when an error occurs
     */
    public void deActivateRegistration(String registrationId) throws Exception {
        //get the right registration
        Registration registration = getRegistration(registrationId);

        //deactivate registration and update the database
        registration.setRegistrationActive(false);

        if (registration.getRegistrationStatus() == 'R' || registration.getRegistrationStatus() == 'P') {
            int enrolled = registration.getClassroom().getEnrolled();
            registration.getClassroom().getRegistrationList().remove(registration);
            registration.getClassroom().setEnrolled(enrolled - 1);
        }

        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        registrationDB.updateRegistration(registration);
    }

    /**
     * Activate registration and updates the database
     *
     * @param registrationId unique id to find registration to be activate
     * @throws java.lang.Exception thrown when an error occurs
     */
    public void activateRegistration(String registrationId) throws Exception {
        //get the right registration
        Registration registration = getRegistration(registrationId);

        //deactivate registration and update database
        registration.setRegistrationActive(true);

        if (registration.getRegistrationStatus() == 'R' || registration.getRegistrationStatus() == 'P') {
            int enrolled = registration.getClassroom().getEnrolled();
            registration.getClassroom().getRegistrationList().add(registration);
            registration.getClassroom().setEnrolled(enrolled + 1);
        }

        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        registrationDB.updateRegistration(registration);
    }

    /**
     * Uses the registration ID to get the registration object
     *
     * @param registrationId id for registration to be obtained
     * @return registration to be obtained
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public Registration getRegistration(String registrationId) throws Exception {
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        Registration registration = registrationDB.getRegisrtation(Integer.parseInt(registrationId));
        return registration;
    }

    /**
     * Retrieves list of pending registrations according to the criteria
     *
     * @param key search criteria of the child name to look for when
     * @return list of registrations
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List<Registration> getPendingRegistrations(String key) throws Exception {
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        //get list of all registrations in the database
        List<Registration> DBregistrations = registrationDB.getAllRegistrations();
        //list to send to the user
        List<Registration> pendingRegistrations = new ArrayList<>();

        //go through the list and look for pending registrations
        for (Registration registration : DBregistrations) {
            if (registration.getRegistrationActive() && registration.getRegistrationStatus() == 'P') {
                pendingRegistrations.add(registration);
            }
        }

        //if the search criteria is not null grab the registrations that match it
        if (key != null) {
            //put key in lower case
            key = key.toLowerCase();
            List<Registration> searchedRegistrations = new ArrayList<>();

            for (Registration registration : pendingRegistrations) {
                //grab each childs name and convert string to lowercase
                String firstName = registration.getChild().getChildFirstName().toLowerCase();
                String lastName = registration.getChild().getChildLastName().toLowerCase();
                String fullName = firstName + " " + lastName;

                if (firstName.contains(key) || lastName.contains(key) || fullName.contains(key)) {
                    searchedRegistrations.add(registration);
                }
            }
            return searchedRegistrations;
        }
        return pendingRegistrations;
    }

    /**
     * Retrieves list of active registrations
     *
     * @param active true if looking for active registrations
     * @param key search criteria of the child name to look for when
     * @return list of registrations
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List<Registration> getRegistrationsByActiveStatus(boolean active, String key) throws Exception {
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        //get list of all registrations in the database
        List<Registration> DBregistrations = registrationDB.getRegistrationsByActiveStatus(active);

        //if the search criteria is not null grab the registrations that match it
        if (key != null) {
            //put key in lower case
            key = key.toLowerCase();
            List<Registration> searchedRegistrations = new ArrayList<>();

            for (Registration registration : DBregistrations) {
                //grab each childs name and convert string to lowercase
                String firstName = registration.getChild().getChildFirstName().toLowerCase();
                String lastName = registration.getChild().getChildLastName().toLowerCase();
                String fullName = firstName + " " + lastName;

                if (firstName.contains(key) || lastName.contains(key) || fullName.contains(key)) {
                    searchedRegistrations.add(registration);
                }
            }
            //return list of registrations that match searh key
            return searchedRegistrations;
        }
        return DBregistrations;
    }

    /**
     * Offers the child a spot in the class and informs parent by email
     *
     * @param registrationId registration to associated with child and class
     * @param path file path for the email template
     * @throws Exception thrown when a database or email service error occurs
     */
    public void offerSpotToWaitList(String registrationId, String path) throws Exception {
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        Registration registration = registrationDB.getRegisrtation(Integer.parseInt(registrationId));

        //change waitlist status to registration fee not paid 
        registration.setRegistrationStatus('N');

        //check if a registration fee instance already exists
        int registrationFee = 0;
        for (PaymentDetails payment : registration.getPaymentDetailsList()) {
            if (payment.getPaymentDescription().toLowerCase().equals("registration fee")) {
                registrationFee++;
                break;
            }
        }

        //make a registration fee instance if one doesn't already exist
        if (registrationFee == 0) {
            makeRegistrationFee(registration);
        } else {
            registrationDB.updateRegistration(registration);
        }

        //prepare the email templates 
        String subject = "Class Spot Open";
        String template = path + "/emailtemplates/spotopen.html";

        //send email
        sendEmailAboutRegistration(subject, template, registration, registration.getChild().getAccount().getEmail());
    }

    /**
     * Withdraw child by deactivating registration
     *
     * @param registrationId ID of registration
     * @param path path for the email templates
     * @throws java.lang.Exception thrown when a database or email service error
     * occurs
     */
    public void withdrawChildRegistration(String registrationId, String path) throws Exception {
        deActivateRegistration(registrationId);
        Registration registration = getRegistration(registrationId);

        String subject = "Registration Withdrawn";
        path += "/emailtemplates/withdraw.html";

        //send email to parent about withdrawal
        sendEmailAboutRegistration(subject, path, registration, registration.getChild().getAccount().getEmail());
        //send email to admin about withdrawal
        sendEmailAboutRegistration(subject, path, registration, "parklandpreschoolteam@gmail.com");
    }

    /**
     * Sends email for things related to the registration (withdrawal, open
     * spot, and successful registration)
     *
     * @param subject subject for the email
     * @param template location of html file for the body of the email
     * @param registration registration specified in the email
     * @param email email to send to
     * @throws Exception thrown when an error with Gmail occurs
     */
    private void sendEmailAboutRegistration(String subject, String template, Registration registration, String email) throws Exception {

        //get account, classroom, and child information
        Account account = registration.getChild().getAccount();
        Child child = registration.getChild();
        Classroom classroom = registration.getClassroom();

        //send email to account that spot is available
        //format days, times, and dates
        SimpleDateFormat date = new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
        String dates = date.format(classroom.getStartDate()) + " - " + date.format(classroom.getEndDate());
        String times = time.format(classroom.getStartDate()) + " - " + time.format(classroom.getEndDate());

        String days = "Tuesday/Thursday";
        if (classroom.getDays().equals("MWF")) {
            days = "Monday/Wednesday/Friday";
        }

        String parent = account.getAccountFirstName() + " " + account.getAccountLastName();

        if (email.equals("parklandpreschoolteam@gmail.com")) {
            parent = "Administrator";
        }

        //create a map for information place on the email
        HashMap<String, String> tags = new HashMap<>();
        tags.put("parent", parent);
        tags.put("child", child.getChildFirstName() + " " + child.getChildLastName());
        tags.put("class", classroom.getDescription());
        tags.put("dates", dates);
        tags.put("time", times);
        tags.put("days", days);

        //send email
        GmailService.sendMail(email, subject, template, tags);
    }

    /**
     * Deletes a registration that is inactive and removes it from the child and
     * classroom list
     *
     * @param registrationId id for the registration to be deleted
     * @throws Exception thrown when an exception occurs
     */
    public void deleteRegistration(String registrationId) throws Exception {
        //get registration, child, and classroom
        Registration registration = getRegistration(registrationId);
        Child child = registration.getChild();
        Classroom classroom = registration.getClassroom();

        if (registration.getRegistrationActive()) {
            throw new IllegalArgumentException("Active registrations can't be deleted");
        }

        //remove registration from child's registration list and classroom list
        child.getRegistrationList().remove(registration);
        classroom.getRegistrationList().remove(registration);

        //updtae the database
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        registrationDB.deleteRegistration(registration);
    }

    /**
     * Retrieves list of all registrations in the database
     *
     * @return List of Registration objects
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List<Registration> getAllRegistrations() throws Exception {
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        return registrationDB.getAllRegistrations();
    }
}
