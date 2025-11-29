package repository;

import entities.Employee;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class EmployeeRepository {
    protected EntityManager em;

//    public EmployeeRepository(EntityManager em) {
//        this.em = em;
//    }

    public Employee findEmpRecordById(Long empNo) {
        em = JPAUtil.getEntityManager();
        return em.find(Employee.class, empNo);

    }
}
