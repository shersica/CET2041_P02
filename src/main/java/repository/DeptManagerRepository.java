package repository;

import entities.DeptManager;
import entities.DeptManagerId;
import jakarta.persistence.EntityManager;

/**
 * Repository providing JPA operations for {@link DeptManager} entity.
 * Does not manage transactions; the caller is responsible for beginning
 * and committing/rolling back transactions.
 */
public class DeptManagerRepository {

    /**
     * Add DeptEmployee object to the database. No return type.
     * @param em active EntityManager
     * @param deptManager DeptManager object to be added
     */
    public void addDeptManager(EntityManager em, DeptManager deptManager) {
        em.persist(deptManager);
    }

    /**
     * Find and return DeptManager object by its composite key, i.e. DeptManagerId
     * @param em active EntityManager
     * @param id DeptManagerId to query
     * @return the DeptManager if found, otherwise null
     */
    public DeptManager findById(EntityManager em, DeptManagerId id) {
        return em.find(DeptManager.class, id);
    }
}
