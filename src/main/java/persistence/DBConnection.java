package persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class allows for a singleton patten for connecting to the database.
 *
 * @author Jean Lomibao
 * @author Nic Kelly
 */
public final class DBConnection {

    //this is the create connection method in the doumentation
    private static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("parklandpreschoolPU");

    /**
     * This returns a connection to the entity manager factory.
     *
     * @return EntityManagerFactory.
     */
    public static EntityManagerFactory getConnection() {
        return emf;
    }
}
