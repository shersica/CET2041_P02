package repository;

import entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DepartmentRepository {

    public List<Department> findAllDepartments(EntityManager em) {
        return em.createNamedQuery("Department.findAllDepartments", Department.class).getResultList();
    }

    public Department findDepartmentById(EntityManager em, String deptNo) {
        return em.find(Department.class, deptNo);
    }

}
