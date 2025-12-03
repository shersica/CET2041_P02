package repository;

import entities.Department;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repository providing JPA operations for {@link Department} entity.
 * Does not manage transactions; the caller is responsible for beginning
 * and committing/rolling back transactions.
 */
public class DepartmentRepository {

    /**
     * Find and return all departments in the company
     * @param em active EntityManager
     * @return list of Departments
     */
    public List<Department> findAllDepartments(EntityManager em) {
        return em.createNamedQuery("Department.findAllDepartments", Department.class).getResultList();
    }

    /**
     * Find and return Department from its unique identifier, i.e. department number
     * @param em active EntityManager
     * @param deptNo unique department identifier
     * @return the Department if found, otherwise null
     */
    public Department findDepartmentById(EntityManager em, String deptNo) {
        return em.find(Department.class, deptNo);
    }

}
