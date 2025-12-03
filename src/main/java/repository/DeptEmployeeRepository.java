package repository;

import entities.DeptEmployee;
import entities.DeptEmployeeId;
import jakarta.persistence.EntityManager;

/**
 * Repository providing JPA operations for {@link DeptEmployee} entity.
 * Does not manage transactions; the caller is responsible for beginning
 * and committing/rolling back transactions.
 */
public class DeptEmployeeRepository {

    /**
     * Add DeptEmployee object to the database. No return type.
     * @param em active EntityManager
     * @param deptEmployee DeptEmployee object to be added
     */
    public void addDeptEmployee(EntityManager em, DeptEmployee deptEmployee) {
        em.persist(deptEmployee);
    }

    /**
     * Find and return DeptEmployee object by its composite key, i.e. DeptEmployeeId
     * @param em active EntityManager
     * @param id DeptEmployeeId to query
     * @return the DeptEmployee if found, otherwise null
     */
    public DeptEmployee findById(EntityManager em, DeptEmployeeId id) {
        return em.find(DeptEmployee.class, id);
    }
}
