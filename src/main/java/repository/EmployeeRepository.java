package repository;

import entities.Department;
import entities.DeptEmployee;
import entities.Employee;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    protected EntityManager em;

    public EmployeeRepository(EntityManager em) {
        this.em = em;
    }

    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }

    public List<Employee> findDeptEmployee(String deptNo) {
        Department department = em.find(Department.class, deptNo);
        List<Employee> employees = new ArrayList<>();

        if (department != null) {
            for (DeptEmployee deptEmployee : department.getDeptEmployees()) {
                employees.add(deptEmployee.getDeptEmpEmployeeObj());
            }
        }
        return employees;
    }
}
