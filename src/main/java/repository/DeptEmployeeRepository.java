package repository;

import entities.DeptEmployee;
import entities.DeptEmployeeId;
import jakarta.persistence.EntityManager;

public class DeptEmployeeRepository {

    public void addDeptEmployee(EntityManager em, DeptEmployee deptEmployee) {
        em.persist(deptEmployee);
    }

    public DeptEmployee findById(EntityManager em, DeptEmployeeId id) {
        return em.find(DeptEmployee.class, id);
    }
}
