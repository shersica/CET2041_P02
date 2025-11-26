import entities.Department;
import entities.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import repository.DepartmentRepository;
import repository.EmployeeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static String DBNAME = "employees";

    public static void main(String[] args) {
        Map<String,String> persistenceMap = new HashMap<>();
        persistenceMap.put("jakarta.persistence.jdbc.url",
                "jdbc:mariadb://localhost:3306/"+ DBNAME);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeRepository", persistenceMap);
        EntityManager em = emf.createEntityManager();
        EmployeeRepository employeeRepository = new EmployeeRepository(em);
        DepartmentRepository departmentRepository = new DepartmentRepository(em);
        Employee employee = null;
        employee = employeeRepository.findById(10001L);
        System.out.println("Employee Found: " + employee);

        List<Department> departments = departmentRepository.findAllDepartments();
        for (Department department : departments) {
            System.out.println("Department Found: " + department);
        }

        em.close();
        emf.close();
    }
}
