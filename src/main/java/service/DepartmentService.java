package service;

import entities.Department;
import jakarta.persistence.EntityManager;
import repository.DepartmentRepository;
import util.JPAUtil;

import java.util.List;

public class DepartmentService {
    private final DepartmentRepository departmentRepository = new DepartmentRepository();

    public List<Department> findAllDepartments() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return departmentRepository.findAllDepartments(em);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving departments", e);
        }
    }

}
