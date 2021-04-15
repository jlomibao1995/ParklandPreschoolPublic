package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import problemdomain.PaymentDetails;
import services.PaymentDetailsService;

/**
 * This class communicates with the database and handles the payments.
 *
 * @author Nic Kelly
 */
public final class PaymentDBBroker {

    /**
     * Search for a payment in the database.
     *
     * @param paymentNum to search for.
     * @return the correct PaymentDetails object reference or null.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public PaymentDetails getPayment(Integer paymentNum) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.find(PaymentDetails.class, paymentNum);
        } catch (Exception ex) {
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve payment: {0}", paymentNum);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Finds the right payment information using the invoice id
     *
     * @param invoiceId unique id for a payment instance
     * @return PaymentDetails object
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public PaymentDetails getPaymentByInvoice(String invoiceId) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("PaymentDetails.findByInvoiceId", PaymentDetails.class)
                    .setParameter("invoiceId", invoiceId).getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve payment: {0}", invoiceId);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Add a payment details object to the database.
     *
     * @param payment to add.
     * @return PaymnetDetails the full payment information
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public PaymentDetails insertPayment(PaymentDetails payment) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            //insert payment first
            trans.begin();
            manager.persist(payment);
            manager.merge(payment.getRegistration());
            trans.commit();

            //set the invoiceId
            payment = manager.find(PaymentDetails.class, payment.getPaymentId());
            trans.begin();
            payment.setInvoiceId(PaymentDetailsService.INVOICE_PREFIX + payment.getPaymentId());
            payment = manager.merge(payment);
            trans.commit();
            return payment;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not insert payment for registration: {0}", payment.getRegistration().getRegistrationId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Update a payment in the database.
     *
     * @param payment to update.
     * @return true if the commit was a success.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public boolean updatePayment(PaymentDetails payment) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(payment);
            manager.merge(payment.getRegistration());
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();;
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not update payment: {0}", payment.getPaymentId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Updates the monthly payment and full payment instance after being paid
     *
     * @param monthlyPayment monthly payment information
     * @param fullPayment full payment information
     * @throws Exception thrown when a database error occurs
     */
    public void updateMonthlyPayment(PaymentDetails monthlyPayment, PaymentDetails fullPayment) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(monthlyPayment);
            manager.merge(fullPayment);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not update monthly payments");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Get the payments in a certain month for the current year.
     *
     * @param paymentMonth to search for payments of.
     * @return the list of payment detail objects.
     * @throws java.lang.Exception thrown when database error occurs
     */
    public List<PaymentDetails> getMonthlyPayments(String paymentMonth) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        try {
            return manager.createNamedQuery("PaymentDetails.findByPaymentMonth").getResultList();
        } catch (Exception e) {
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve payments for: {0}", paymentMonth);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Get all payments in the database.
     *
     * @return a list of all the payments.
     * @throws java.lang.Exception thrown when a DB error occurs
     */
    public List<PaymentDetails> getAllPayments() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("PaymentDetails.findAll", PaymentDetails.class).getResultList();
        } catch (Exception e) {
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve payments");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Retrieves list of unique month and year payment
     *
     * @return list of PaymentDetails object
     * @throws Exception thrown when a database error occurs
     */
    public List<String> getDistinctMonths() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        try {
            List<String> months = manager.createNamedQuery("PaymentDetails.findUniqueMonths", String.class).getResultList();
            return months;
        } catch (Exception e) {
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve distinct months");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Delete a payment object from the database.
     *
     * @param payment to delete.
     * @return true if the commit was a success.
     * @throws Exception thrown when a database error occurs
     */
    public boolean deletePayment(PaymentDetails payment) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.remove(manager.merge(payment));
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not delete payment: {0}", payment.getPaymentId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Retrieves the instances for the full payments
     *
     * @return PaymentDetails object
     * @throws Exception thrown when a database error occurs
     */
    public List<PaymentDetails> getFullPayments() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            //find the information for the full payment instances
            List<PaymentDetails> payments = manager.createNamedQuery("PaymentDetails.findByPaymentDescription", PaymentDetails.class)
                    .setParameter("paymentDescription", "full payment").getResultList();
            return payments;
        } catch (Exception e) {
            Logger.getLogger(PaymentDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve full payments");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }
}
