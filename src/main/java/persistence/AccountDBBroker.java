package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import problemdomain.Account;

/**
 * This class communicates with the database and handles the accounts.
 *
 * @author Nic Kelly
 * @author Jean Lomibao
 * @version January 30, 2021
 */
public final class AccountDBBroker {

    /**
     * Gets an account from an account number.
     *
     * @param accountNum to search for the account of.
     * @return the account or null if not found.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public Account getAccount(Integer accountNum) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            Account account = manager.find(Account.class, accountNum);
            return account;
        } catch (Exception ex) {
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not find the account with the account ID: {0}", accountNum);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Retrieves the account with the given email
     *
     * @param email email of the account
     * @return account object holding the account information from database
     * @throws NoResultException thrown if there are no accounts with the given
     * email
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public Account getAccountByEmail(String email) throws NoResultException, Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            Account account = manager.createNamedQuery("Account.findByEmail", Account.class).setParameter("email", email).getSingleResult();
            return account;
        } catch (Exception ex) {
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not find the account with the account email: {0}", email);
            
            if (ex instanceof NoResultException){
                throw new NoResultException();
            }
            
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Updated an account in the database.
     *
     * @param account to update.
     * @return true if the account was updated.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public boolean updateAccount(Account account) throws IllegalStateException, Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(account);
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not update the account: {0}", account.getAccountId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Return a list of all accounts in the database.
     *
     * @return list of accounts.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List getAllAccounts() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("Account.findAll", Account.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve accounts");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Deletes an account from the database.
     *
     * @param account to delete.
     * @return true if the account was deleted successfully.
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public boolean deleteAccount(Account account) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.remove(manager.merge(account));
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not delete account: {0}", account.getAccountId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Insert an account into the database.
     *
     * @param account to insert.
     * @throws java.lang.Exception thrown when a database error occurs
     * @throws IllegalStateException thrown when a unique constraint is violated
     */
    public void insertAccount(Account account) throws IllegalStateException, Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.persist(account);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not insert new account: {0}", account.getEmail());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Finds the account with the associated UUID
     *
     * @param uuid unique id
     * @return Account with the given id
     * @throws Exception thrown when a database error occurs
     */
    public Account getAccountByActivateUUID(String uuid) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            Account account = manager.createNamedQuery("Account.findByActivateAccountUuid", Account.class).setParameter("activateAccountUuid", uuid).getSingleResult();
            return account;
        } catch (Exception ex) {
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve account with uuid: {0}", uuid);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Retrieves the account according to the reset uuid
     *
     * @param uuid unique id for an account
     * @return account object
     * @throws Exception thrown when a database error occurs
     */
    public Account getAccountByResetUUID(String uuid) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            Account account = manager.createNamedQuery("Account.findByResetPasswordUuid", Account.class).setParameter("resetPasswordUuid", uuid).getSingleResult();
            return account;
        } catch (Exception ex) {
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve account with uuid: {0}", uuid);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Gets a list of account based on their status
     *
     * @param active true if accounts are to be active
     * @return list of either active or inactive accounts
     * @throws Exception thrown when a database error occurs
     */
    public List<Account> getAccountsByStatus(boolean active) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            List<Account> accounts = manager.createNamedQuery("Account.findByAccountStatus", Account.class).setParameter("accountStatus", active).getResultList();
            return accounts;
        } catch (Exception ex) {
            Logger.getLogger(AccountDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve accounts by status");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }
}
