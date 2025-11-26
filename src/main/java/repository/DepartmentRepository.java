package repository;

import entities.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DepartmentRepository {
    protected EntityManager em;

    public DepartmentRepository(EntityManager em) {
        this.em = em;
    }

    public List<Department> findAllDepartments() {
        TypedQuery<Department> query = em.createQuery(
                "SELECT e FROM Department e", Department.class);
        return query.getResultList();
    }
}
