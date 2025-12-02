package repository;

import entities.DeptEmployee;
import entities.DeptEmployeeId;
import jakarta.persistence.EntityManager;

public class DeptEmployeeRepository {
//    protected EntityManager em;
//
//    public DeptEmployeeRepository(EntityManager em) {
//        this.em = em;
//    }

    public void addDeptEmployee(EntityManager em, DeptEmployee deptEmployee) {
        System.out.println("add deptEmployee");
        em.persist(deptEmployee);
    }

    public DeptEmployee findById(EntityManager em, DeptEmployeeId id) {
        return em.find(DeptEmployee.class, id);
    }
}
