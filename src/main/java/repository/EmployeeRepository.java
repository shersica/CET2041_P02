package repository;

import entities.Employee;
import jakarta.persistence.EntityManager;

public class EmployeeRepository {
    protected EntityManager em;

    public EmployeeRepository(EntityManager em) {
        this.em = em;
    }

    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }


}
