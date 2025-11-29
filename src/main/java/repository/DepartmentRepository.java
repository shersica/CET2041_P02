package repository;

import entities.Department;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

import java.util.List;

public class DepartmentRepository {
    protected EntityManager em;

//    public DepartmentRepository(EntityManager em) {
//        this.em = em;
//    }

    public List<Department> findAllDepartments() {
        em = JPAUtil.getEntityManager();
        return em.createNamedQuery("Department.findAllDepartments", Department.class).getResultList();

    }
}
