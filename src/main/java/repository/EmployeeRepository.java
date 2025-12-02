package repository;

import dtos.EmployeeDTO;
import dtos.PromotionRequestDTO;
import entities.*;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeRepository {

    public Employee findById(EntityManager em, Long id) {
        return em.find(Employee.class, id);
    }

    public List<EmployeeDTO> findEmployeesByDept(EntityManager em, String deptNo, int page) {
        return em.createNamedQuery("Employee.findEmployeeInDepartment", EmployeeDTO.class)
                .setParameter("deptNo", deptNo)
                .setFirstResult((page - 1) * 20)
                .setMaxResults(20)
                .getResultList();
    }
}
