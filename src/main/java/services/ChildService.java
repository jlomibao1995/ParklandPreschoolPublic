package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import persistence.AccountDBBroker;
import persistence.ChildDBBroker;
import problemdomain.Account;
import problemdomain.AuthorizedPickup;
import problemdomain.Child;
import problemdomain.EmergencyContact;

/**
 * Accesses the preschool database and manages the Child table
 *
 * @author Jean Lomibao
 */
public class ChildService {

    /**
     * Checks if user info is valid using a regular expression
     *
     * @param input user input to check
     * @param regExp regular expression to check the input against
     * @param message message to display if input is not valid
     */
    public void inputValidator(String input, String regExp, String message) {
        if (!input.matches(regExp) && !input.equals("") && !input.equals(null)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Connects to the database and inserts a child record to the Child table
     *
     * @param accountId account child belongs to
     * @param childToAdd child to be added
     * @param accountRelation relation of account owner to child
     * @param guardian2 second guardian mother or father
     * @param emergency emergency contact for child
     * @param otherCareGiver other care giver for child
     * @param authorized1 authorized pickup info
     * @param authorized2 second authorized pickup info
     * @throws Exception thrown when a database error occurs
     */
    public void addChild(String accountId, Child childToAdd, String accountRelation, EmergencyContact guardian2,
            EmergencyContact emergency, EmergencyContact otherCareGiver,
            AuthorizedPickup authorized1, AuthorizedPickup authorized2) throws Exception {

        inputValidator(accountRelation, "[a-zA-Z-\\s]+", "Account relation can't contain special characters");

        //grab the account the child will be added to ;
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = accountDB.getAccount(Integer.parseInt(accountId));

        //add the account to the child and vice versa
        childToAdd.setAccount(account);
        account.getChildList().add(childToAdd);

        //update account relation
        account.setRelationToChild(accountRelation.toUpperCase());

        //insert the record to the database and update account
        ChildDBBroker childDB = new ChildDBBroker();
        childDB.addChild(childToAdd, account, guardian2, otherCareGiver, emergency, authorized1, authorized2);
    }

    /**
     * Checks if the user inputs are valid then creates a new child instance
     *
     * @param gender child's gender
     * @param firstName child's first name
     * @param lastName child's last name
     * @param address child's address
     * @param postalCode child's postal code
     * @param doctor child's doctor
     * @param clinic child's clinic
     * @param immunization if child's shots are updated
     * @param medications if child takes medications
     * @param hadChickenPox if child has had chicken pox before
     * @param medicalPhoneNumber clinic/doctor's phone number
     * @param healthCareNum child's AB health care number
     * @param allergy child's allergies if any
     * @param medicalConditions child's medical conditions if any
     * @param month month of child's birthday
     * @param day day of child's birthday
     * @param year year of child's birthday
     * @return Child instance
     * @throws java.text.ParseException thrown if date is not formatted properly
     */
    public Child getChildInstance(String gender, String firstName,
            String lastName, String address, String postalCode, String doctor,
            String clinic, String immunization, String medications, String hadChickenPox,
            String medicalPhoneNumber, String healthCareNum, String allergy, String medicalConditions,
            String month, String day, String year) throws ParseException, NullPointerException {
        
        inputValidator(gender, "[a-zA-z]+", "Child's gender can't contain special characters");
        inputValidator(firstName, "[a-zA-z-\\s]+", "Child's name can't contain special characters");
        inputValidator(lastName, "[a-zA-z-\\s]+", "Child's name can't contain special characters");
        inputValidator(address, "[a-zA-z0-9-\\.\\s]+", "Child's address can't contain special characters");
        inputValidator(postalCode , "[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]", "Child's postal code doesn't match the required format");
        inputValidator(doctor, "[a-zA-z-\\.\\s]+", "Child's doctor can't contain special characters");
        inputValidator(clinic, "[a-zA-z-\\.\\s]+", "Child's clinic can't contain special characters");
        inputValidator(medications, "[a-zA-z]+", "Child's medications can't contain special characters");
        inputValidator(medicalPhoneNumber, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Child's phone number doesn't match the required format");
        inputValidator(healthCareNum, "[0-9-]+", "Child's healthcare number can't contain special characters");
        inputValidator(allergy, "[a-zA-z-,\\.\\s|^$]+", "Child's allergy can't contain special characters");
        inputValidator(medicalConditions, "[a-zA-z-,\\.\\s]+", "Child's medical conditions can't contain special characters");
        inputValidator(month, "[0-9]+", "Child's birth month can't contain special characters");
        inputValidator(day, "[0-9]+", "Child's birth day can't contain special characters");
        inputValidator(year, "[0-9]+", "Child's birth year can't contain special characters");
        
        
        //if any of the inputs are blank throw an error
        if (doctor.equals("") && clinic.equals("")) {
            throw new IllegalArgumentException("Doctor or clinic must be filled in");
        }
        
        Date birthdate = new SimpleDateFormat("MM/d/yyyy").parse(month + "/" + day + "/" + year);
        int age = Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), LocalDate.now()).getYears();

        //if age is less than 3 or more than 5 throw an error
        if (age > 5 || age <= 2) {
            throw new IllegalArgumentException("Only children ages 3-5 can be added");
        }

        //find if immunizations and chicken pox are true or false
        boolean immunizationsUpdated = Boolean.valueOf(immunization);
        boolean chickenPox = Boolean.valueOf(hadChickenPox);
        boolean medication = Boolean.valueOf(medications);

        //create a Child instance with inputs given
        Child child = new Child(0, birthdate, gender.charAt(0), firstName, lastName,
                address, "Calgary", "AB", postalCode, doctor, clinic, immunizationsUpdated,
                chickenPox, medicalPhoneNumber, healthCareNum, allergy);

        child.setMedications(medication);
        child.setMedicalConditions(medicalConditions);

        return child;
    }

    /**
     * Checks that user input for second guardian is valid
     *
     * @param firstName second guardian's first name
     * @param lastName second guardian's last name
     * @param address second guardian's address
     * @param homePhone second guardian's home phone
     * @param workPhone second guardian's work phone
     * @param cellPhone second guardian's cell phone
     * @param relation second guardian's relation to child
     * @return guardian instance as EmergencyContacts object
     */
    public EmergencyContact getGuardian2Instance(String firstName, String lastName, String address,
            String homePhone, String workPhone, String cellPhone, String relation) {
        
        inputValidator(firstName, "[a-zA-z-\\.\\s]+", "Guardian's first name can't contain special characters");
        inputValidator(lastName, "[a-zA-z-\\.\\s]+", "Guardian's last name can't contain special characters");
        inputValidator(address, "[a-zA-z0-9-\\.\\s]+", "Guardian's address can't contain special characters");
        inputValidator(homePhone, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Guardian's phone number doesn't match the required format");
        inputValidator(workPhone, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Guardian's phone number doesn't match the required format");
        inputValidator(cellPhone,"^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Guardian's phone number doesn't match the required format");
        inputValidator(relation, "[a-zA-z-\\s]+", "Guardian's relation can't contain special characters");
        
        //check if user input is valid
        if (firstName.equals("") || lastName.equals("") || address.equals("")
                || homePhone.equals("") || workPhone.equals("") || cellPhone.equals("")
                || relation.equals("")) {
            throw new NullPointerException("Guardian information is required");
        }

        //create emergencycontact instance
        EmergencyContact guardian2 = new EmergencyContact(0, homePhone);
        guardian2.setEmergencyFirstName(firstName);
        guardian2.setEmergencyLastName(lastName);
        guardian2.setEmergencyAddress(address);
        guardian2.setWorkPhoneNumber(workPhone);
        guardian2.setCellphoneNumber(cellPhone);
        guardian2.setRelationToChild(relation.toUpperCase());

        return guardian2;
    }

    /**
     * Checks that user input is valid
     *
     * @param firstName other caregiver's first name
     * @param lastName other caregiver's last name
     * @param address other care giver's address
     * @param phoneNumber other care giver's phone number
     * @return caregiver instance as EmergencyContacts object
     */
    public EmergencyContact getOtherCaregiverInstance(String firstName,
            String lastName, String address, String phoneNumber) {
        
        inputValidator(firstName, "[a-zA-z-\\.\\s]+", "Other caregiver's first name can't contain special characters");
        inputValidator(lastName, "[a-zA-z-\\.\\s]+", "Other caregiver's last name can't contain special characters");
        inputValidator(address, "[a-zA-z0-9-\\.\\s]+", "Other caregiver's address can't contain special characters");
        inputValidator(phoneNumber,"^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Other caregiver's phone number doesn't match the required format");
        
        //check if user input is valid
        if (firstName.equals("") || lastName.equals("") || address.equals("")
                || phoneNumber.equals("")) {
            throw new IllegalArgumentException("Other care giver information is required");
        }

        //create emergencycontact instance
        EmergencyContact other = new EmergencyContact(0, phoneNumber);
        other.setEmergencyFirstName(firstName);
        other.setEmergencyLastName(lastName);
        other.setEmergencyAddress(address);

        return other;
    }

    /**
     * Checks that user input is valid and creates a new EmergencyContacts
     * instance
     *
     * @param firstName emergency contact's first name
     * @param lastName emergency contact's last name
     * @param address emergency contact's address
     * @param homePhone emergency contact's home phone
     * @param cellPhone emergency contact's cell phone
     * @param relation emergency contact's relaiton to child
     * @return emergency contact as an EmergencyContacts object
     */
    public EmergencyContact getEmergencyContactInstance(String firstName, String lastName,
            String address, String homePhone, String cellPhone, String relation) {
        
        
        inputValidator(firstName, "[a-zA-z-\\.\\s]+", "Emergency contact's first name can't contain special characters");
        inputValidator(lastName, "[a-zA-z-\\.\\s]+", "Emergency contact's last name can't contain special characters");
        inputValidator(address, "[a-zA-z0-9-\\.\\s]+", "Emergency contact'saddress can't contain special characters");
        inputValidator(homePhone, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Emergency contact's phone number doesn't match the required format");
        inputValidator(cellPhone,"^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Emergency contact'sphone number doesn't match the required format");
        inputValidator(relation, "[a-zA-z-\\s]+", "Emergency contact's relation can't contain spechial characters");
        
        
        //check if user input is valid
        if (firstName.equals("") || lastName.equals("") || address.equals("")
                || homePhone.equals("") || cellPhone.equals("")
                || relation.equals("")) {
            throw new NullPointerException("Emergency Contact information is required");
        }

        //create emergencycontact instance
        EmergencyContact emergencyContact = new EmergencyContact(0, homePhone);
        emergencyContact.setEmergencyFirstName(firstName);
        emergencyContact.setEmergencyLastName(lastName);
        emergencyContact.setEmergencyAddress(address);
        emergencyContact.setCellphoneNumber(cellPhone);
        emergencyContact.setRelationToChild(relation.toUpperCase());

        return emergencyContact;
    }

    /**
     * Checks that the user input is valid and creates a new AuthorizedPickup
     * instance
     *
     * @param firstName first name of authorized pickup
     * @param lastName last name of authorized pickup
     * @return AuthorizedPickup instance
     */
    public AuthorizedPickup getAuthorizedPickUpInstance(String firstName, String lastName) {
        
        inputValidator(firstName, "[a-zA-z-\\.\\s]+", "Authorized pickup's first name can't contain special characters");
        inputValidator(lastName, "[a-zA-z-\\.\\s]+", "Authorized pickup's last name can't contain special characters");
        
        
        //check if user input is valid
        if (firstName.equals("") || lastName.equals("")) {
            throw new NullPointerException("Authorized pickup information is required");
        }

        AuthorizedPickup authorizedPickup = new AuthorizedPickup(0, firstName, lastName, " ");
        return authorizedPickup;
    }

    /**
     * If a child does not have any registrations either active or non-active
     * the child can be deleted from the database by the guardian
     *
     * @param childId child being deleted
     * @param accountId account the child belongs to
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void deleteChildForGuardian(String childId, String accountId) throws Exception {

        //get child record and account from the database
        Child child = getChild(childId);
        Account account = child.getAccount();

        AccountDBBroker accountDB = new AccountDBBroker();
        Account loggedAccount = accountDB.getAccount(Integer.parseInt(accountId));

        //check if child belongs to account
        if (loggedAccount.getAccountId() != account.getAccountId()) {
            throw new IllegalArgumentException("This account has no priveleges to delete child from account");
        }

        //check if child is registered to a class
        if (child.getRegistrationList().size() != 0) {
            throw new IllegalArgumentException("Child has a registration to 1 or more classes");
        }

        //remove child from account
        account.getChildList().remove(child);

        //update database and check if updates are successful
        accountDB.updateAccount(account);
        ChildDBBroker childDB = new ChildDBBroker();
        childDB.deleteChild(child);
    }

    /**
     * Updates child information and saves them to the database
     *
     * @param accountId account child belongs to
     * @param updatedChild update child information
     * @param accountRelation relation of child to account
     * @param updatedGuardian2 updated guardian information
     * @param updatedEmergency updated emergency contact information
     * @param updatedOtherCareGiver updated other caregiver information
     * @param updatedAuthorized1 updated authorized pickup information
     * @param updatedAuthorized2 updated authorized pickup information
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void updateChildInformation(String accountId, Child updatedChild, String accountRelation,
            EmergencyContact updatedGuardian2, EmergencyContact updatedEmergency, EmergencyContact updatedOtherCareGiver,
            AuthorizedPickup updatedAuthorized1, AuthorizedPickup updatedAuthorized2)
            throws Exception {

        //grab the account the child will be added to ;
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = accountDB.getAccount(Integer.parseInt(accountId));

        //update all fields that need to be updated
        ChildDBBroker childDB = new ChildDBBroker();
        Child childInDB = childDB.getChild(updatedChild.getChildId());

        //check if child belongs to the account
        if (account.getAccountId() != childInDB.getAccount().getAccountId()) {
            throw new IllegalArgumentException("This account has no priveleges to update child");
        }

        account.setRelationToChild(accountRelation);

        //grab all the information that needs to be updated
        EmergencyContact guardian2 = childInDB.getEmergencyContactList().get(0);
        EmergencyContact emergency = childInDB.getEmergencyContactList().get(1);
        EmergencyContact other = childInDB.getEmergencyContactList().get(2);
        AuthorizedPickup authorized1 = childInDB.getAuthorizedPickupList().get(0);
        AuthorizedPickup authorized2 = childInDB.getAuthorizedPickupList().get(1);

        //update child information
        childInDB.setChildFirstName(updatedChild.getChildFirstName());
        childInDB.setChildLastName(updatedChild.getChildLastName());
        childInDB.setChildAddress(updatedChild.getChildAddress());
        childInDB.setChildBirthdate(updatedChild.getChildBirthdate());
        childInDB.setChildGender(updatedChild.getChildGender());
        childInDB.setAllergy(updatedChild.getAllergy());
        childInDB.setHealthcareNum(updatedChild.getHealthcareNum());
        childInDB.setDoctor(updatedChild.getDoctor());
        childInDB.setClinic(updatedChild.getClinic());
        childInDB.setMedicalPhoneNumber(updatedChild.getMedicalPhoneNumber());
        childInDB.setImmunizationUpdated(updatedChild.getImmunizationUpdated());
        childInDB.setHadChickenPox(updatedChild.getHadChickenPox());
        childInDB.setMedicalConditions(updatedChild.getMedicalConditions());
        childInDB.setMedications(updatedChild.getMedications());

        //update second guardian information
        guardian2.setEmergencyFirstName(updatedGuardian2.getEmergencyFirstName());
        guardian2.setEmergencyLastName(updatedGuardian2.getEmergencyLastName());
        guardian2.setEmergencyAddress(updatedGuardian2.getEmergencyAddress());
        guardian2.setWorkPhoneNumber(updatedGuardian2.getWorkPhoneNumber());
        guardian2.setHomePhoneNumber(updatedGuardian2.getHomePhoneNumber());
        guardian2.setCellphoneNumber(updatedGuardian2.getCellphoneNumber());
        guardian2.setRelationToChild(updatedGuardian2.getRelationToChild());

        //update emergency contact
        emergency.setEmergencyFirstName(updatedEmergency.getEmergencyFirstName());
        emergency.setEmergencyLastName(updatedEmergency.getEmergencyLastName());
        emergency.setEmergencyAddress(updatedEmergency.getEmergencyAddress());
        emergency.setHomePhoneNumber(updatedEmergency.getHomePhoneNumber());
        emergency.setCellphoneNumber(updatedEmergency.getCellphoneNumber());
        emergency.setRelationToChild(updatedEmergency.getRelationToChild());

        //update other care giver
        other.setEmergencyFirstName(updatedOtherCareGiver.getEmergencyFirstName());
        other.setEmergencyLastName(updatedOtherCareGiver.getEmergencyLastName());
        other.setEmergencyAddress(updatedOtherCareGiver.getEmergencyAddress());
        other.setHomePhoneNumber(updatedOtherCareGiver.getHomePhoneNumber());

        //update authorized pickups
        authorized1.setAuthorizedFirstName(updatedAuthorized1.getAuthorizedFirstName());
        authorized1.setAuthorizedLastName(updatedAuthorized1.getAuthorizedLastName());
        authorized2.setAuthorizedFirstName(updatedAuthorized2.getAuthorizedFirstName());
        authorized2.setAuthorizedLastName(updatedAuthorized2.getAuthorizedLastName());

        childInDB.getEmergencyContactList().set(0, guardian2);
        childInDB.getEmergencyContactList().set(1, emergency);
        childInDB.getEmergencyContactList().set(2, other);
        childInDB.getAuthorizedPickupList().set(0, authorized1);
        childInDB.getAuthorizedPickupList().set(1, authorized2);

        childDB.updateChild(childInDB, account, guardian2, other, emergency, authorized1, authorized2);
    }

    /**
     * Retrieves child object from the database
     *
     * @param childId id for the child
     * @return Child object to be retrieved
     * @throws java.lang.Exception thrown when a database occurs
     */
    public Child getChild(String childId) throws Exception {
        ChildDBBroker childDB = new ChildDBBroker();
        Child child = childDB.getChild(Integer.parseInt(childId));
        return child;
    }

    /**
     * Returns a list of all children in the database
     *
     * @return list of Child objects
     * @throws Exception thrown when a database error occurs
     */
    public List<Child> getAllChildren() throws Exception {
        ChildDBBroker childDB = new ChildDBBroker();
        return childDB.getAllChildren();
    }
}
