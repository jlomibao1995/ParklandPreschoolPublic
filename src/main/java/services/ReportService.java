package services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import persistence.AccountDBBroker;
import problemdomain.Account;
import problemdomain.AuthorizedPickup;
import problemdomain.Child;
import problemdomain.EmergencyContact;
import problemdomain.Registration;

/**
 * Creates PDF files for registration forms and account reports
 * @author Jean Lomibao
 */
public class ReportService {

    /**
     * Returns the registration form as a PDF file that can be downloaded
     * @param registrationId identifies registration to be placed on the form
     * @param path root path to find the template form
     * @return the data stream where the pdf was saved on
     * @throws Exception thrown when a data base error occurs
     */
    public byte[] getRegistrationForm(String registrationId, String path) throws Exception {
        
        //grabs the template pdf for filling in registration form
        String formTemplate = path + "/documents/Parkland-Registration_Read_Only.pdf";
        PDDocument pdfDocument = PDDocument.load(new File(formTemplate));
        PDAcroForm pdAcroForm = pdfDocument.getDocumentCatalog().getAcroForm();

        //retrive registration and populate form with information
        RegistrationService registrationService = new RegistrationService();
        Registration registration = registrationService.getRegistration(registrationId);
        populateRegistrationForm(pdAcroForm, registration);

        //store form in byte array and return it
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pdfDocument.save(baos);

        pdfDocument.close();
        byte[] pdfData = baos.toByteArray();
        return pdfData;

    }

    public byte[] printAllRegistrationForms(String path, List<Registration> registrations) throws IOException, ParseException {
        String formTemplate = path + "/documents/Parkland-Registration_Read_Only.pdf";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //main document
        PDDocument document = new PDDocument();

        //Instantiating PDFMergerUtility class for merging pdfs
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        //go through the whole list of registrations fill a registration form for each one
        for (Registration registration : registrations) {
            //load the pdf form from the file system
            PDDocument pdfDocument = PDDocument.load(new File(formTemplate));
            //grab the acroform to fill in the fields with data
            PDAcroForm pdAcroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            populateRegistrationForm(pdAcroForm, registration);

            //adds a form for each registration
            PDFmerger.appendDocument(document, pdfDocument);
        }
        
        //save the pdf form to an output stream of bytes
        document.save(baos);
        byte[] data = baos.toByteArray();
        return data;
    }

    /**
     * Fills in the fields in the Registration form with the registration information
     * @param pdAcroForm form to be filled
     * @param registration registration information to fill the form with
     * @throws IOException thrown when there is an error with the acro form
     * @throws ParseException thrown when the data can't be parsed
     * @throws NullPointerException thrown when the field specified is not found on the form
     */
    private void populateRegistrationForm(PDAcroForm pdAcroForm, Registration registration) throws IOException, ParseException, NullPointerException {
        //figure out school year
        String year = new SimpleDateFormat("yyyy").format(registration.getClassroom().getStartDate());
        int currentYear = Integer.parseInt(year);
        int nextYear = currentYear + 1;
        pdAcroForm.getField("year").setValue(year + "/" + nextYear);

        //figure out times
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(registration.getClassroom().getStartDate());
        int startHour = startDate.get(Calendar.HOUR);
        int startMinute = startDate.get(Calendar.MINUTE);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(registration.getClassroom().getEndDate());
        int endHour = endDate.get(Calendar.HOUR);
        int endMinute = endDate.get(Calendar.MINUTE);

        //check the right classroom by checking the right checkbox in the form 
        if (registration.getClassroom().getDays().equals("TTh")) {
            ((PDCheckBox) pdAcroForm.getField("twoDays")).check();

            //compare times for the first Tues/Thur class
            Calendar tuesThursStart = Calendar.getInstance();
            tuesThursStart.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2021-03-09 9:00"));
            int ttStartHour = tuesThursStart.get(Calendar.HOUR);
            int ttStartMinute = tuesThursStart.get(Calendar.MINUTE);

            Calendar tuesThursEnd = Calendar.getInstance();
            tuesThursEnd.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2021-03-09 11:15"));
            int ttEndHour = tuesThursEnd.get(Calendar.HOUR);
            int ttEndMinute = tuesThursEnd.get(Calendar.MINUTE);

            if (startHour == ttStartHour && startMinute == ttStartMinute && endHour == ttEndHour && endMinute == ttEndMinute) {
                ((PDCheckBox) pdAcroForm.getField("TThu3_1")).check();
            } else {
                ((PDCheckBox) pdAcroForm.getField("TThu3_2")).check();
            }

        } else {
            ((PDCheckBox) pdAcroForm.getField("threeDays")).check();

            if (registration.getClassroom().getAgeGroup() == 4) {
                ((PDCheckBox) pdAcroForm.getField("MWF4")).check();
            } else {
                ((PDCheckBox) pdAcroForm.getField("MWFKinder")).check();
            }
        }

        //get child information
        Child child = registration.getChild();
        pdAcroForm.getField("childName").setValue(child.getChildFirstName() + " " + child.getChildLastName());
        pdAcroForm.getField("birthdate").setValue(new SimpleDateFormat("MMM dd yyyy").format(child.getChildBirthdate()));
        pdAcroForm.getField("childAddress").setValue(child.getChildAddress());
        pdAcroForm.getField("childPostalCode").setValue(child.getChildPostalCode());

        if (child.getChildGender() == 'F') {
            ((PDCheckBox) pdAcroForm.getField("girl")).check();
        } else {
            ((PDCheckBox) pdAcroForm.getField("boy")).check();
        }

        pdAcroForm.getField("healthCareNum").setValue(child.getHealthcareNum());
        pdAcroForm.getField("allergies").setValue(child.getAllergy());

        pdAcroForm.getField("email").setValue(child.getAccount().getEmail());
        //get parent information       
        if (child.getAccount().getRelationToChild().equals("MOTHER")) {
            Account mother = child.getAccount();
            EmergencyContact father = child.getEmergencyContactList().get(0);

            //fill mother's information
            pdAcroForm.getField("motherName").setValue(mother.getAccountFirstName() + " " + mother.getAccountLastName());
            pdAcroForm.getField("motherHomePhone").setValue(mother.getAccountPhoneNumber());
            pdAcroForm.getField("motherWorkPhone").setValue(mother.getWorkPhoneNumber());
            pdAcroForm.getField("motherCellPhone").setValue(mother.getCellphoneNumber());
            pdAcroForm.getField("motherAddress").setValue(mother.getAccountAddress());

            //fill father's information
            pdAcroForm.getField("fatherName").setValue(father.getEmergencyFirstName() + " " + father.getEmergencyLastName());
            pdAcroForm.getField("fatherHomePhone").setValue(father.getHomePhoneNumber());
            pdAcroForm.getField("fatherWorkPhone").setValue(father.getWorkPhoneNumber());
            pdAcroForm.getField("fatherCellPhone").setValue(father.getCellphoneNumber());
            pdAcroForm.getField("fatherAddress").setValue(father.getEmergencyAddress());
        } else {
            Account father = child.getAccount();
            EmergencyContact mother = child.getEmergencyContactList().get(0);

            //fill mother's information
            pdAcroForm.getField("motherName").setValue(mother.getEmergencyFirstName() + " " + mother.getEmergencyLastName());
            pdAcroForm.getField("motherHomePhone").setValue(mother.getHomePhoneNumber());
            pdAcroForm.getField("motherWorkPhone").setValue(mother.getWorkPhoneNumber());
            pdAcroForm.getField("motherCellPhone").setValue(mother.getCellphoneNumber());
            pdAcroForm.getField("motherAddress").setValue(mother.getEmergencyAddress());

            //fill father's information
            pdAcroForm.getField("fatherName").setValue(father.getAccountFirstName() + " " + father.getAccountLastName());
            pdAcroForm.getField("fatherHomePhone").setValue(father.getAccountPhoneNumber());
            pdAcroForm.getField("fatherWorkPhone").setValue(father.getWorkPhoneNumber());
            pdAcroForm.getField("fatherCellPhone").setValue(father.getCellphoneNumber());
            pdAcroForm.getField("fatherAddress").setValue(father.getAccountAddress());
        }

        //fill in other caregiver information
        EmergencyContact other = child.getEmergencyContactList().get(2);
        pdAcroForm.getField("otherName").setValue(other.getEmergencyFirstName() + " " + other.getEmergencyLastName());
        pdAcroForm.getField("otherPhone").setValue(other.getHomePhoneNumber());
        pdAcroForm.getField("otherAddress").setValue(other.getEmergencyAddress());

        //fill in emergency contact information
        EmergencyContact emergency = child.getEmergencyContactList().get(1);
        pdAcroForm.getField("emergencyName").setValue(emergency.getEmergencyFirstName() + " " + emergency.getEmergencyLastName());
        pdAcroForm.getField("emergencyHomePhone").setValue(emergency.getHomePhoneNumber());
        pdAcroForm.getField("emergencyCellPhone").setValue(emergency.getCellphoneNumber());
        pdAcroForm.getField("emergencyAddress").setValue(emergency.getEmergencyAddress());
        pdAcroForm.getField("emergencyRelation").setValue(emergency.getRelationToChild());

        //fill in signatures
        String signature = child.getAccount().getAccountFirstName() + " " + child.getAccount().getAccountLastName();
        pdAcroForm.getField("policySignature").setValue(signature);
        pdAcroForm.getField("safetySignature").setValue(signature);
        pdAcroForm.getField("outdoorsSignature").setValue(signature);
        pdAcroForm.getField("sickSignature").setValue(signature);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        pdAcroForm.getField("safetyDate").setValue(dateFormat.format(registration.getSafetySigDateStamp()));
        pdAcroForm.getField("outdoorsDate").setValue(dateFormat.format(registration.getOutdoorSigDateStamp()));
        pdAcroForm.getField("sickDate").setValue(dateFormat.format(registration.getSickSigDateStamp()));
        pdAcroForm.getField("policyDate").setValue(dateFormat.format(registration.getDisciplineSigDateStamp()));

        //fill in authorized pickup
        AuthorizedPickup authorized1 = child.getAuthorizedPickupList().get(0);
        AuthorizedPickup authorized2 = child.getAuthorizedPickupList().get(1);
        pdAcroForm.getField("authorized1Name").setValue(authorized1.getAuthorizedFirstName() + " " + authorized1.getAuthorizedLastName());
        pdAcroForm.getField("authorized2Name").setValue(authorized2.getAuthorizedFirstName() + " " + authorized2.getAuthorizedLastName());

        //fill in immunizations 
        if (child.getImmunizationUpdated()) {
            ((PDCheckBox) pdAcroForm.getField("immunizationYes")).check();
        } else {
            ((PDCheckBox) pdAcroForm.getField("immunizationNo")).check();
        }

        //fill in chicken pox
        if (child.getHadChickenPox()) {
            ((PDCheckBox) pdAcroForm.getField("poxYes")).check();
        } else {
            ((PDCheckBox) pdAcroForm.getField("poxNo")).check();
        }

        //fill in medical information
        pdAcroForm.getField("doctorClinic").setValue(child.getDoctor() + ", " + child.getClinic());
        pdAcroForm.getField("medicalPhone").setValue(child.getMedicalPhoneNumber());

        if (child.getMedicalConditions() != null && !child.getMedicalConditions().equals("")) {
            ((PDCheckBox) pdAcroForm.getField("medicalConditionsYes")).check();
            pdAcroForm.getField("medicalConditions").setValue(child.getMedicalConditions());
        } else {
            ((PDCheckBox) pdAcroForm.getField("medicalConditionsNo")).check();
        }

        if (child.getMedications()) {
            ((PDCheckBox) pdAcroForm.getField("medicationsYes")).check();
        } else {
            ((PDCheckBox) pdAcroForm.getField("medicationsNo")).check();
        }

    }

    /**
     * Grabs list of either active or inactive accounts and generates a pdf form
     * @param active true for active accounts and false for active accounts
     * @param path root path to find the template form
     * @return array of bytes where form is saved 
     * @throws Exception thrown when an error occurs with the file or database
     */
    public byte[] printAccountsByStatus(boolean active, String path) throws Exception {
        //get all active accounts
        AccountDBBroker accountDB = new AccountDBBroker();
        List<Account> accounts = accountDB.getAccountsByStatus(active);

        String formTemplate = path + "/documents/4x10-Form.pdf";

        //main document
        PDDocument document = new PDDocument();

        //find how many pages are needed 
        int pages = 1;
        if (accounts.size() % 10 == 0) {
            pages = accounts.size() / 10;
        } else {
            pages = accounts.size() / 10 + 1;
        }

        //Instantiating PDFMergerUtility class for merging pdfs
        PDFMergerUtility PDFmerger = new PDFMergerUtility();

        //create a page for every 10 accounts
        for (int page = 1; page <= pages; page++) {
            PDDocument pdfDocument = PDDocument.load(new File(formTemplate));
            PDAcroForm pdAcroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            //set title of form
            if (active) {
                pdAcroForm.getField("title").setValue("Active Accounts Report");
            } else {
                pdAcroForm.getField("title").setValue("Inactive Accounts Report");
            }

            //set headings, date, and page
            pdAcroForm.getField("heading1").setValue("Email");
            pdAcroForm.getField("heading2").setValue("Name");
            pdAcroForm.getField("heading3").setValue("Last Login Date");
            pdAcroForm.getField("heading4").setValue("Type");
            pdAcroForm.getField("date_af_date").setValue(new Date().toString());
            pdAcroForm.getField("page").setValue(String.format("%s", page));

            //keep track of accounts already displayed
            int numOfAccounts = 0;

            while (numOfAccounts < 10 && numOfAccounts*page < accounts.size()) {
                //get the account and set the data to be placed in the report for each account
                Account account = accounts.get(numOfAccounts * page);
                populateAccountPDF(account, pdAcroForm, numOfAccounts+1);
                numOfAccounts++;
            }
            
            //add the page to the main document
            PDFmerger.appendDocument(document, pdfDocument);
        }

        //save the document in byte array then return it
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
        byte[] pdfData = baos.toByteArray();
        return pdfData;
    }

    /**
     * Fills in the form for each account 
     * @param account account object containing information to be displayed
     * @param pdAcroForm form to be filled
     * @param y_axis current row in the form
     * @throws IOException thrown when an error occurs with the file
     */
    public void populateAccountPDF(Account account, PDAcroForm pdAcroForm, int y_axis) throws IOException {
        //grab the information that is to be placed in the form from the account
        String email = account.getEmail();
        String name = account.getAccountFirstName() + " " + account.getAccountLastName();
        String loginDate = new SimpleDateFormat("dd-MM-yyyy hh:mma").format(account.getLastLoginDate());
        char accountType = account.getAccountType();

        //determine type of account
        String type = "";
        switch (accountType) {
            case 'A':
                type = "Admin";
                break;
            case 'S':
                type = "Staff";
                break;
            case 'G':
                type = "Guardian";
        }

        //get the current row based on y_axis
        String row = String.format("%s", y_axis);
        int column = 1;

        //fill in the form based on column and row in the form 
        pdAcroForm.getField(column + row).setValue(email);
        pdAcroForm.getField(++column + row).setValue(name);
        pdAcroForm.getField(++column + row).setValue(loginDate);
        pdAcroForm.getField(++column + row).setValue(type);
    }
}
