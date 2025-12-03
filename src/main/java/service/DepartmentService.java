package service;

import entities.Department;
import jakarta.persistence.EntityManager;
import repository.DepartmentRepository;
import util.JPAUtil;

import java.util.List;

/**
 * Service responsible for handling Departments-related operations,
 * e.g. search and display the departments.
 */
public class DepartmentService {

    /**
     * Repository responsible for accessing and modifying {@link Department} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final DepartmentRepository departmentRepository = new DepartmentRepository();

    /**
     * Find and return all departments in the company
     * @return list of {@code Department} objects
     */
    public List<Department> findAllDepartments() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return departmentRepository.findAllDepartments(em);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving departments", e);
        }
    }

}
