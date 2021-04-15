package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import problemdomain.Classroom;

/**
 * This class communicates with the database and handles the classrooms.
 *
 * @author Nic Kelly
 */
public final class ClassroomDBBroker {

    /**
     * Adds a classroom to the database.
     *
     * @param classroom to add.
     * @return true if the addition was a success.
     * @throws Exception thrown when a database error occurs
     */
    public boolean insertClassroom(Classroom classroom) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(classroom);
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ClassroomDBBroker.class.getName()).log(Level.SEVERE, "Could not insert classroom: {0}", classroom.getDescription());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Update a classroom in the database.
     *
     * @param classroom to update.
     * @return true if the update was a success.
     * @throws Exception if there is a database error
     */
    public boolean updateClassroom(Classroom classroom) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.merge(classroom);
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ClassroomDBBroker.class.getName()).log(Level.SEVERE, "Could not update classroom: {0}", classroom.getClassroomId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Return a registration object from the database.
     *
     * @param classroomNum of the classroom.
     * @return the classroom found or null.
     * @throws Exception thrown when a database error occurs
     */
    public Classroom getClassroom(Integer classroomNum) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.find(Classroom.class, classroomNum);
        } catch (Exception ex) {
            Logger.getLogger(ClassroomDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve classroom: {0}", classroomNum);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Get a list of all the classes.
     *
     * @return a list of all the classes.
     * @throws Exception thrown when a database error occurs
     */
    public List<Classroom> getAllClassrooms() throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("Classroom.findAll", Classroom.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(ClassroomDBBroker.class.getName()).log(Level.SEVERE, "Could not retrieve classrooms");
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Delete a classroom from the database.
     *
     * @param classroom to delete.
     * @return true if the commit was a success.
     * @throws Exception thrown when a database error occurs
     */
    public boolean deleteClassroom(Classroom classroom) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();
        EntityTransaction trans = manager.getTransaction();
        try {
            trans.begin();
            manager.remove(manager.merge(classroom));
            trans.commit();
            return true;
        } catch (Exception e) {
            trans.rollback();
            Logger.getLogger(ClassroomDBBroker.class.getName()).log(Level.SEVERE, "Could not delete classroom: {0}", classroom.getClassroomId());
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

    /**
     * Finds a list of classroom of the same age group
     *
     * @param age age group of classroom
     * @return List of Classroom objects
     * @throws Exception thrown when a database error occurs
     */
    public List<Classroom> getClassroomByAge(int age) throws Exception {
        EntityManager manager = DBConnection.getConnection().createEntityManager();

        try {
            return manager.createNamedQuery("Classroom.findByAgeGroup", Classroom.class).setParameter("ageGroup", age).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(ClassroomDBBroker.class.getName()).log(Level.SEVERE, "Could not delete classrooms with age group: {0}", age);
            throw new Exception("Database error");
        } finally {
            manager.close();
        }
    }

}
