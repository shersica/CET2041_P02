package repository;

import dtos.EmployeeDTO;
import entities.*;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repository providing JPA operations for {@link Employee} entity.
 * Does not manage transactions; the caller is responsible for beginning
 * and committing/rolling back transactions.
 */
public class EmployeeRepository {

    /**
     * Find and return Employee object from its unique identifier, i.e. employee number
     * @param em active EntityManager
     * @param id unique employee identifier, i.e. employee number
     * @return the Employee if found, otherwise null
     */
    public Employee findById(EntityManager em, Long id) {
        return em.find(Employee.class, id);
    }

    /**
     * Find and return a list of employee DTOs based on the department
     * unique identifier, i.e. department number, and the page number
     * @param em active EntityManager
     * @param deptNo unique department identifier, i.e. department number
     * @param page page number to be displayed
     * @return a list of employee DTOs if found, otherwise null
     */
    public List<EmployeeDTO> findEmployeesByDept(EntityManager em, String deptNo, int page) {
        return em.createNamedQuery("Employee.findEmployeeInDepartment", EmployeeDTO.class)
                .setParameter("deptNo", deptNo)
                .setFirstResult((page - 1) * 20)
                .setMaxResults(20)
                .getResultList();
    }
}
