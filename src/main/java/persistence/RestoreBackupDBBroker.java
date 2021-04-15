package persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import problemdomain.Backup;

/**
 * This class communicates with the database and handles the backups.
 *
 * @author Nic Kelly
 */
public final class RestoreBackupDBBroker {

    /**
     * Retrieves a backup from a certain date.
     *
     * @param date to look for a backup.
     * @return the backup object reference or null if not found.
     */
    public Backup getBackup(DateTime date) {
        return null;
    }
    
    public Backup getBackup(String backupId) { //not in design doc not needed
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        return manager.find(Backup.class, Integer.valueOf(backupId));
    }

    /**
     * Returns a list of all the backups in the database.
     *
     * @return list of backups.
     */
    public List getBackups() {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        //refactor this and test before return

        //manager.close()
        return manager.createNamedQuery("Backup.findAll", Backup.class).getResultList();
    }

    /**
     * Insert a backup into the database.
     *
     * @param backup to insert.
     * @return true if the commit was a success.
     */
    public boolean insertBackup(Backup backup) {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(backup);
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            return false;
        } finally {
            manager.close();
        }
    }

    /**
     * Deletes a backup from the database.
     *
     * @param backup to delete from the database.
     * @return true if the commit was a success.
     */
    public boolean deleteBackup(Backup backup) {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.remove(manager.merge(backup));
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            return false;
        } finally {
            manager.close();
        }
    }
}
