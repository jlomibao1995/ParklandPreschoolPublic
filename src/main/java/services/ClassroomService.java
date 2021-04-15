package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import persistence.ClassroomDBBroker;
import persistence.RegistrationDBBroker;
import problemdomain.Account;
import problemdomain.Child;
import problemdomain.Classroom;
import problemdomain.Registration;

/**
 * Accesses preschool database and manages the Classroom table
 *
 * @author Nic Kelly, Jean Lomibao
 */
public class ClassroomService {

    /**
     * Retrieves classes that the child can register for
     *
     * @param child child to be registered
     * @return list of classrooms available for the child
     * @throws java.lang.Exception throw when a database error occurs
     */
    public List<Classroom> getClassroomsForChild(Child child) throws Exception {
        ClassroomDBBroker classDB = new ClassroomDBBroker();
        ArrayList<Classroom> classes = new ArrayList<>();

        //get list of classrooms and list of registrations the child has
        List<Classroom> DBclasses = classDB.getClassroomByAge(child.getAge());
        List<Registration> registrations = child.getRegistrationList();

        //go through all the classrooms in the database
        for (Classroom classroom : DBclasses) {

            //only get classrooms for ones that haven't ended
            if (new Date().before(classroom.getEndDate())) {

                //check if the child has already been registered for the class
                //if child has no registrations just add the class
                if (!registrations.isEmpty()) {
                    boolean registered = false;
                    for (Registration registration : registrations) {
                        if (Objects.equals(registration.getClassroom().getClassroomId(), classroom.getClassroomId())) {
                            registered = true;
                        }
                    }

                    if (!registered) {
                        classes.add(classroom);
                    }

                } else {
                    classes.add(classroom);
                }
            }
        }

        return classes;
    }

    /**
     * Return a class given the class id as a string
     *
     * @param classId to search for
     * @return the classroom object or null
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public Classroom getClassroom(String classId) throws Exception {
        ClassroomDBBroker classBroker = new ClassroomDBBroker();
        return classBroker.getClassroom(Integer.valueOf(classId));
    }

    /**
     * Delete a classroom
     *
     * @param c classroom to delete
     * @throws java.lang.Exception thrown if database error is encountered
     */
    public void deleteClassroom(Classroom c) throws Exception {
        ClassroomDBBroker classBroker = new ClassroomDBBroker();
        classBroker.deleteClassroom(c);
    }

    /**
     * Update a classroom
     *
     * @param description Class description
     * @param ageGroup of class (3 or 4)
     * @param days days of week class runs
     * @param capacity max number of students enrolled
     * @param accountId of staff in charge of class
     * @param startDate of class
     * @param endDate of class
     * @param year of class
     * @param cost of class
     * @param c classroom to update
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void updateClassroom(String description, String ageGroup, String days,
            String capacity, String accountId, String startDate, String endDate,
            String year, String cost, Classroom c) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        AccountService accountService = new AccountService();

        int classAge = Integer.parseInt(ageGroup);

        if (c.getAgeGroup() != classAge && !c.getRegistrationList().isEmpty()) {
            throw new IllegalArgumentException("Class already has registered children, age can't be modified");
        }

        //update the classroom with given parameters
        c.setAgeGroup(Integer.parseInt(ageGroup));
        c.setDays(days);
        c.setCapacity(Integer.valueOf(capacity));
        c.setAccount(accountService.getAccount(accountId));

        c.setDescription(description);
        c.setStartDate(format.parse(startDate));
        c.setEndDate(format.parse(endDate));
        c.setYear(Integer.valueOf(year));
        c.setCostPerMonth(Double.valueOf(cost));

        ClassroomDBBroker classBroker = new ClassroomDBBroker();
        classBroker.updateClassroom(c);

    }

    /**
     * Get all classroom objects from the database
     *
     * @return a list for all the classes
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List getAllClassrooms() throws Exception {
        ClassroomDBBroker classBroker = new ClassroomDBBroker();
        return classBroker.getAllClassrooms();
    }

    /**
     * Insert a class to the database
     *
     * @param description class description
     * @param days of the week the class runs
     * @param capacity max number of students
     * @param accountId of the staff in charge
     * @param startDate of the class
     * @param endDate of the class
     * @param year of the class
     * @param ageGroup of the class
     * @param cost of the class
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void insertClassroom(String description, String days, String capacity,
            String accountId, String startDate, String endDate,
            String year, String ageGroup, String cost) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        ClassroomDBBroker classBroker = new ClassroomDBBroker();
        AccountService accountService = new AccountService();

        if (days.equals("")) {
            throw new IllegalArgumentException("No days selected for class");
        }

        Classroom classroom = new Classroom(0, Integer.parseInt(ageGroup), days);

        classroom.setDescription(description);
        classroom.setCapacity(Integer.parseInt(capacity));
        
        //make sure account is a staff account
        Account teacher = accountService.getAccount(accountId);
        if (teacher.getAccountType() == 'G'){
            throw new IllegalArgumentException("Account has to be a staff or admin to teach a class");
        }
        classroom.setAccount(teacher);
        classroom.setStartDate(format.parse(startDate));
        classroom.setEndDate(format.parse(endDate));
        classroom.setYear(Integer.valueOf(year));
        classroom.setCostPerMonth(Double.valueOf(cost));
        classBroker.insertClassroom(classroom);

    }

    /**
     * Finds any unpaid registrations and sets registration status to W
     *
     * @param classroom classroom with registrations to check
     * @param path path to find email templates
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public void waitList(Classroom classroom, String path) throws Exception {

        for (Registration registration : classroom.getRegistrationList()) {
            if (registration.getRegistrationActive() && registration.getRegistrationStatus() == 'N') {
                registration.setRegistrationStatus('W');

                //update registration in database
                RegistrationDBBroker registrationDB = new RegistrationDBBroker();
                registrationDB.updateRegistration(registration);

                Account account = registration.getChild().getAccount();
                Child child = registration.getChild();

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

                //send an email for each child that has been waitlisted
                GmailService.sendMail(account.getEmail(), subject, template, tags);
            }
        }
    }

    /**
     * Changes the classroom child is registered to
     *
     * @param registrationId ID of registration
     * @param classId ID of class
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void moveChildToClass(String registrationId, String classId) throws Exception {
        //get the registration and classroom object
        RegistrationDBBroker registrationDB = new RegistrationDBBroker();
        Registration registration = registrationDB.getRegisrtation(Integer.parseInt(registrationId));
        Classroom classroom = getClassroom(classId);

        //check if class to be changed to is valid
        if (new Date().after(classroom.getEndDate()) || classroom.getAgeGroup() != registration.getChild().getAge()) {
            throw new IllegalArgumentException("Invalid class");
        }

        //change classrooms and enrollment numbers
        Classroom oldClass = registration.getClassroom();
        oldClass.getRegistrationList().remove(registration);
        oldClass.setEnrolled(oldClass.getEnrolled() - 1);
        registration.setClassroom(classroom);
        classroom.getRegistrationList().add(registration);
        classroom.setEnrolled(classroom.getEnrolled() + 1);

        //update changes in database
        registrationDB.updateRegistration(registration, oldClass);
    }
}
