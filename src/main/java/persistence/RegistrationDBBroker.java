package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import problemdomain.Child;
import problemdomain.Classroom;
import problemdomain.PaymentDetails;
import problemdomain.Registration;

/**
 * This class communicates with the database and handles the registration.
 *
 * @author Nic Kelly
 */
public final class RegistrationDBBroker {

    /**
     * Update a registration in the database.
     *
     * @param registration to update.
     * @return true if the commit was a success.
     * @throws Exception if there is a database error
     */
    public boolean updateRegistration(Registration registration) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(registration);
            manager.merge(registration.getClassroom());
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not update registration: {0}", registration.getRegistrationId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Saves the information changed in the database registration is moved to a
     * different class
     *
     * @param registration registration to be changed
     * @param oldClass the old classroom the registration was for
     * @throws Exception thrown when a database error occurs
     */
    public void updateRegistration(Registration registration, Classroom oldClass) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(registration);
            manager.merge(registration.getClassroom());
            manager.merge(oldClass);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not update registration: {0}", registration.getRegistrationId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Retrieve a registration from the database.
     *
     * @param registrationId to search for.
     * @return the registration object reference or null.
     * @throws Exception thrown when a database error occurs
     */
    public Registration getRegisrtation(Integer registrationId) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.find(Registration.class, registrationId);
        } catch (Exception e) {
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not retieve registration: {0}", registrationId);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Return all the registrations in the database.
     *
     * @return a list or registrations.
     * @throws Exception thrown when a database error occurs
     */
    public List<Registration> getAllRegistrations() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("Registration.findAll", Registration.class).getResultList();
        } catch (Exception e) {
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not retieve registrations");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Delete a registration from the database and update the child and class
     * registration lists
     *
     * @param registration to delete.
     * @return true if the commit was a success.
     * @throws Exception thrown when a database error occurs
     */
    public boolean deleteRegistration(Registration registration) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(registration.getChild());
            manager.merge(registration.getClassroom());
            manager.remove(manager.merge(registration));
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not delete registration: {0}", registration.getRegistrationId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Adds all the necessary information required for registration as a unit
     *
     * @param child child information for child being registered
     * @param classroom classroom that child is registered to
     * @param registration registration information
     * @throws Exception thrown when a database error occurs
     */
    public void addChildRegistration(Child child, Classroom classroom, Registration registration) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();

        try {
            trans.begin();
            manager.persist(registration);
            manager.merge(child);
            manager.merge(classroom);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not add registration for child: {0}", child.getChildId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Registration and associated data is updated in the database after payment
     *
     * @param registration registration to be updated
     * @param payment payment associated with registration
     * @param classroom classroom associated with registration
     * @throws Exception thrown when a database error occurs
     */
    public void updateAfterRegistrationFeePayment(Registration registration,
            PaymentDetails payment, Classroom classroom) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();

        try {
            trans.begin();
            manager.merge(registration);
            manager.merge(payment);
            manager.merge(classroom);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not update registration for payment: {0}", registration.getRegistrationId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Gets list of registrations according to active status
     *
     * @param active true for active registrations
     * @return List of Registrations objects
     * @throws Exception thrown when a database error occurs 
     */
    public List<Registration> getRegistrationsByActiveStatus(boolean active) throws Exception{
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("Registration.findByRegistrationActive", Registration.class).setParameter("registrationActive", active).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(RegistrationDBBroker.class.getName()).log(Level.SEVERE, "Could not retieve registrations by status");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }
}
