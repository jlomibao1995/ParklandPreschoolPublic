package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import problemdomain.Account;
import problemdomain.AuthorizedPickup;
import problemdomain.Child;
import problemdomain.EmergencyContact;

/**
 * This class communicates with the database and handles the children.
 *
 * @author Nic Kelly
 */
public final class ChildDBBroker {

    /**
     * Adds child and other information related to the child in the database
     *
     * @param child child instance containing info to be inserted in the
     * database
     * @param account account associated with child
     * @param guardian2 EmergencyContact instance containing guardian info
     * @param other EmergencyContact instance containing other caregiver info
     * @param emergencyContact EmergencyContact instance containing emergency
     * contact info
     * @param authorized1 AuthorizedPickup instance contianing authorized pickup
     * info
     * @param authorized2 AuthorizedPickup instance contianing authorized pickup
     * info
     * @return true if child and associated information was inserted into db
     * successfully
     * @throws Exception thrown when a database error occurs
     */
    public boolean addChild(Child child, Account account, EmergencyContact guardian2,
            EmergencyContact other, EmergencyContact emergencyContact,
            AuthorizedPickup authorized1, AuthorizedPickup authorized2) throws Exception {
        //get connection to database
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            //add child to DB and update account
            manager.persist(child);
            Account updatedAccount = (Account) manager.merge(account);

            //find child instance 
            Child persistedChild = null;
            for (Child childInAccount : updatedAccount.getChildList()) {
                if (childInAccount.getChildFirstName().equals(child.getChildFirstName())
                        && childInAccount.getChildLastName().equals(child.getChildLastName())) {
                    persistedChild = childInAccount;
                }
            }

            //add child to guardian2, other, and authorized instances
            guardian2.setChild(persistedChild);
            emergencyContact.setChild(persistedChild);
            other.setChild(persistedChild);
            authorized1.setChild(persistedChild);
            authorized2.setChild(persistedChild);

            //add emergency contact instances to database
            manager.persist(guardian2);
            manager.flush();
            manager.persist(emergencyContact);
            manager.flush();
            manager.persist(other);
            manager.flush();
            manager.persist(authorized1);
            manager.persist(authorized2);

            //add authorized pickup  and emergency contacts
            persistedChild.getAuthorizedPickupList().add(authorized1);
            persistedChild.getAuthorizedPickupList().add(authorized2);
            persistedChild.getEmergencyContactList().add(guardian2);
            persistedChild.getEmergencyContactList().add(emergencyContact);
            persistedChild.getEmergencyContactList().add(other);

            manager.merge(persistedChild);

            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ChildDBBroker.class.getName()).log(Level.SEVERE, "Could not insert child for account: {0}", account.getAccountId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Updates child information in the database
     *
     * @param child child to be updated
     * @param account account associated to child
     * @param guardian2 child's second guardian
     * @param other other caregiver's information
     * @param emergencyContact emergency contact information
     * @param authorized1 authorized pickup information
     * @param authorized2 authorized pickup information
     * @throws Exception when a database error occurs
     */
    public void updateChild(Child child, Account account, EmergencyContact guardian2,
            EmergencyContact other, EmergencyContact emergencyContact,
            AuthorizedPickup authorized1, AuthorizedPickup authorized2) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            //update all entity classes associated with child information
            manager.merge(account);
            manager.merge(guardian2);
            manager.merge(other);
            manager.merge(emergencyContact);
            manager.merge(authorized1);
            manager.merge(authorized2);
            manager.merge(child);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ChildDBBroker.class.getName()).log(Level.SEVERE, "Could not update child: {0}", child.getChildId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Return a child from the database.
     *
     * @param childId child to search for.
     * @return the child or null.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public Child getChild(Integer childId) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            Child child = manager.find(Child.class, childId);
            return child;
        } catch (Exception ex) {
            Logger.getLogger(ChildDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve child: {0}", childId);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Return a list of all children in the database.
     *
     * @return list of children.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List getAllChildren() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            List<Child> children = manager.createNamedQuery("Child.findAll", Child.class).getResultList();
            return children;
        } catch (Exception ex) {
            Logger.getLogger(ChildDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve child list");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }

    }

    /**
     * Delete a child from the database.
     *
     * @param child to delete.
     * @return true if the commit was a success.
     * @throws Exception if there is a database error
     */
    public boolean deleteChild(Child child) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(child.getAccount());
            manager.remove(manager.merge(child));
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ChildDBBroker.class.getName()).log(Level.SEVERE, "Could not delete child: {0}", child.getChildId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

}
