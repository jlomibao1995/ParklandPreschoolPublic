package services;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import persistence.PaymentDBBroker;
import persistence.RegistrationDBBroker;
import problemdomain.Account;
import problemdomain.Child;
import problemdomain.Classroom;
import problemdomain.PaymentDetails;
import problemdomain.Registration;

/**
 * Accesses preschool database and manages the Payment_Details table
 *
 * @author Jean Lomibao
 */
public class PaymentDetailsService {

    public static final String INVOICE_PREFIX = "NN"; //invoice prefix to identify payments

    /**
     * Retrieves payment details from the database
     *
     * @param paymentId id to identify the payment details
     * @return Payment object containing details of the payment
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public PaymentDetails getPaymentDetails(String paymentId) throws Exception {
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        PaymentDetails payment = null;
        payment = paymentDB.getPayment(Integer.parseInt(paymentId));
        return payment;
    }

    /**
     * Retrieves list of all payment objects in database
     *
     * @return list of PaymentDetails object
     * @throws Exception thrown when database error occurs
     */
    public List<PaymentDetails> getAll() throws Exception {
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        return paymentDB.getAllPayments();
    }

    /**
     * Retrieves list payment months in the database
     *
     * @return list of payment months
     * @throws Exception thrown when database error occurs
     */
    public List<String> getDistinctMonths() throws Exception {
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        List<String> months = paymentDB.getDistinctMonths();
        return months;
    }

    /**
     * Creates a payment object for the current month for the registration
     *
     * @param registration registration the payment is for
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public void createMonthlyPayment(Registration registration) throws Exception {
        Date currentDate = new Date();

        if (!registration.getIsFullyPaid() && currentDate.after(registration.getClassroom().getStartDate())
                && currentDate.before(registration.getClassroom().getEndDate()) && registration.getRegistrationActive()) {
            //format month 
            String paymentMonth = (LocalDate.now()).getMonth().toString();
            paymentMonth = paymentMonth.substring(0, 1) + paymentMonth.substring(1).toLowerCase();
            paymentMonth += " " + LocalDate.now().getYear();

            //make payment instance
            PaymentDetails payment = new PaymentDetails(0, 'N', registration.getClassroom().getCostPerMonth(), registration.getClassroom().getCostPerMonth(), paymentMonth);
            payment.setPaymentDescription("monthly payment");
            payment.setPaymentMethod("Not paid");
            registration.getPaymentDetailsList().add(payment);
            payment.setRegistration(registration);

            //save information to database
            PaymentDBBroker paymentDB = new PaymentDBBroker();
            paymentDB.insertPayment(payment);
        }

    }

    /**
     * Takes the processed payment and updates the database
     *
     * @param transaction payment transaction
     * @param payment payment information
     * @param payerInfo payer information
     * @param path to find email template for sending email to parent
     * @throws java.lang.Exception thrown when database or email service error
     * occurs
     */
    public void updateDBAfterPayment(Transaction transaction, Payment payment, PayerInfo payerInfo, String path) throws Exception {
        //get the invoice id made when making the registration
        String invoiceId = transaction.getInvoiceNumber();
        PaymentDBBroker paymentDB = new PaymentDBBroker();

        //only continue if the payment has been processed on PayPal's side
        if (!payment.getState().equals("approved")) {
            throw new IllegalStateException("PayPal payment not approved");
        }

        //update the payment details 
        PaymentDetails paymentDetails = paymentDB.getPaymentByInvoice(invoiceId);
        paymentDetails.setPaymentStatus('P');
        //set the payer as the payer's email taken from PayPal
        paymentDetails.setPayer(payerInfo.getEmail());

//        //set payee info as either the email or name of the payee provided by PayPal
        //paymentDetails.setPayee(transaction.getPayee().getEmail());
        paymentDetails.setPayee(transaction.getPayee().getEmail());
        paymentDetails.setPaymentDate(new Date());
        paymentDetails.setPaymentMethod("PayPal");

        switch ((paymentDetails.getPaymentDescription().toLowerCase())) {
            //if the fee is a registration fee then update the classes 
            case "registration fee":
                Registration registration = paymentDetails.getRegistration();
                Classroom classroom = registration.getClassroom();

                registration.setRegistrationStatus('P');
                classroom.setEnrolled(classroom.getEnrolled() + 1);

                RegistrationDBBroker registrationDB = new RegistrationDBBroker();
                registrationDB.updateAfterRegistrationFeePayment(registration, paymentDetails, classroom);

                //prepare the email templates 
                String subject = "Registration Successful";
                String template = path + "/emailtemplates/childregistered.html";

                //get account and child information
                Account account = registration.getChild().getAccount();
                Child child = registration.getChild();

                //send email to account that registration has been processed
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

                //if class is full add any unpaid registrations to waitlist
                if (classroom.getEnrolled() == classroom.getCapacity()) {
                    ClassroomService classService = new ClassroomService();
                    classService.waitList(classroom, path);
                }

                //make lumpsum payment instance
                makeLumpSumPayment(registration, classroom);

                break;
            case "monthly payment":
                //adjust the full payment for the rest of the year
                PaymentDetails fullPayment = updateLumpSumPayment(paymentDetails.getRegistration());
                //update the payments in the database if a monthly fee
                paymentDB.updateMonthlyPayment(paymentDetails, fullPayment);
                break;
            case "full payment":
                //if payment is fully paid update the registration to fully paid
                paymentDetails.getRegistration().setIsFullyPaid(true);
                paymentDB.updatePayment(paymentDetails);

        }
    }

    /**
     * Updates the lump sum payment according to how many months are left to be
     * paid
     *
     * @param registration registration to be paid for
     * @return the updated payment object
     * @throws Exception thrown when a database error occurs
     */
    public PaymentDetails updateLumpSumPayment(Registration registration) throws Exception {
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        List<PaymentDetails> payments = paymentDB.getFullPayments();
        PaymentDetails fullPayment = null;

        for (PaymentDetails payment : payments) {
            if (Objects.equals(payment.getRegistration().getRegistrationId(), registration.getRegistrationId())) {
                fullPayment = payment;
                break;
            }
        }

        double monthlyCost = registration.getClassroom().getCostPerMonth();

        //subtract cost of one month from the current total 
        fullPayment.setPaymentSubtotal(fullPayment.getPaymentSubtotal() - monthlyCost);
        fullPayment.setPaymentTotal(fullPayment.getPaymentTotal() - monthlyCost);
        fullPayment.setPaymentDate(new Date());

        //get month
        String paymentMonth = (LocalDate.now()).getMonth().toString();
        paymentMonth = paymentMonth.substring(0, 1) + paymentMonth.substring(1).toLowerCase();
        paymentMonth += " " + LocalDate.now().getYear();
        fullPayment.setPaymentMonth(paymentMonth);

        return fullPayment;
    }

    /**
     * Calculates the total amount that needs to be paid for the whole year
     *
     * @param registration registration being paid
     * @param classroom classroom registered to
     * @throws Exception thrown when a database error occurs
     */
    public void makeLumpSumPayment(Registration registration, Classroom classroom) throws Exception {
        //get number of months to pay for based on start and end date of class
        Date startDate = classroom.getStartDate();
        Date endDate = classroom.getEndDate();
        double time;

        //determine if time of registration ia before or after school starts
        if (classroom.getStartDate().after(new Date())) {
            time = (double) (endDate.getTime() - startDate.getTime());
        } else {
            time = (double) (endDate.getTime() - new Date().getTime());
        }

        double calculatedMonths = time / (1000.0 * 60.0 * 60.0 * 24.0 * 30);
        int months = (int) Math.floor(calculatedMonths);

        //calculate the total cost for a year
        double cost = months * classroom.getCostPerMonth();

        //get month
        String paymentMonth = (LocalDate.now()).getMonth().toString();
        paymentMonth = paymentMonth.substring(0, 1) + paymentMonth.substring(1).toLowerCase();
        paymentMonth += " " + LocalDate.now().getYear();

        //make a payment instance
        PaymentDetails paymentDetails = new PaymentDetails(0, 'N', cost, cost, paymentMonth);
        paymentDetails.setPaymentMethod("Not paid");
        paymentDetails.setPaymentDescription("full payment");
        paymentDetails.setPaymentDate(new Date());
        paymentDetails.setRegistration(registration);
        registration.getPaymentDetailsList().add(paymentDetails);

        //add to the database
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        paymentDB.insertPayment(paymentDetails);

    }

    /**
     * Return list of payments for a specific class
     *
     * @param classId class to look for
     * @return list of PaymentDetails object
     * @throws Exception thrown when a database error occurs
     */
    public List<PaymentDetails> getPaymentsByClass(String classId) throws Exception {
        //get all payments in the database
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        List<PaymentDetails> allPayments = paymentDB.getAllPayments();
        Iterator<PaymentDetails> iter = allPayments.iterator();

        //go through list and remove payments that dont match the id
        while (iter.hasNext()) {
            if (iter.next().getRegistration().getClassroom().getClassroomId() != Integer.parseInt(classId)) {
                iter.remove();
            }
        }
        return allPayments;
    }

    /**
     * Returns a list of payments made in the specified month
     *
     * @param month month payments were made
     * @return list of PaymentDetails object
     * @throws Exception thrown when a database error occurs
     */
    public List<PaymentDetails> getPaymentsByMonth(String month) throws Exception {
        if (month.equals("") || month == null) {
            throw new NullPointerException("No month specified");
        }

        //get all payments in the database
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        List<PaymentDetails> allPayments = paymentDB.getAllPayments();
        Iterator<PaymentDetails> iter = allPayments.iterator();

        //go through list and remove payments that are not in the specified month
        while (iter.hasNext()) {
            if (!iter.next().getPaymentMonth().equals(month)) {
                iter.remove();
            }
        }
        return allPayments;
    }

    /**
     * Returns a list of payments made for the specified child
     *
     * @param childId child to look for
     * @return list of PaymentDetails object
     * @throws Exception thrown when a database error occurs
     */
    public List<PaymentDetails> getPaymentsByChild(String childId) throws Exception {
        //get all payments in the database
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        List<PaymentDetails> allPayments = paymentDB.getAllPayments();
        Iterator<PaymentDetails> iter = allPayments.iterator();

        //go through list and remove payments that are not for the specified child
        while (iter.hasNext()) {
            if (iter.next().getRegistration().getChild().getChildId() != Integer.parseInt(childId)) {
                iter.remove();
            }
        }
        return allPayments;
    }

    /**
     * Admin can set another method of payment
     *
     * @param paymentId id of the payment instance
     * @param method method of payment
     * @throws Exception thrown when a database error occurs
     */
    public void payWithOtherMethod(String paymentId, String method) throws Exception {
        //get payment object
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        PaymentDetails payment = paymentDB.getPayment(Integer.parseInt(paymentId));

        //update status, method, and date
        payment.setPaymentStatus('P');
        payment.setPaymentMethod(method);
        payment.setPaymentDate(new Date());

        //determine what is being paid to update the necessary information
        switch (payment.getPaymentDescription()) {
            case "monthly payment":
                //adjust the full payment for the rest of the year
                PaymentDetails fullPayment = updateLumpSumPayment(payment.getRegistration());
                //update the payments in the database if a monthly fee
                paymentDB.updateMonthlyPayment(payment, fullPayment);
                break;
            case "full payment":
                //if payment is fully paid update the registration to fully paid
                payment.getRegistration().setIsFullyPaid(true);
                paymentDB.updatePayment(payment);
                break;
            default:
                paymentDB.updatePayment(payment);
        }
    }

    /**
     * Retrieves list of all payments in the database
     * @return PaymentDetails objects
     * @throws Exception when a database error occurs
     */
    public List<PaymentDetails> getAllPayments() throws Exception{
        PaymentDBBroker paymentDB = new PaymentDBBroker();
        return paymentDB.getAllPayments();
    }
}
