package repository;

import entities.DeptEmployeeId;
import entities.DeptManager;
import entities.DeptManagerId;
import jakarta.persistence.EntityManager;

public class DeptManagerRepository {

    public void addDeptManager(EntityManager em, DeptManager deptManager) {
        System.out.println("add deptManager");
        em.persist(deptManager);
    }

    public DeptManager findById(EntityManager em, DeptManagerId id) {
        return em.find(DeptManager.class, id);
    }
}
