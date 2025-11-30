package repository;

import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeRepository {
    @PersistenceContext
    protected EntityManager em;

    private static final int PAGE_SIZE = 20;

//    public EmployeeRepository(EntityManager em) {
//        this.em = em;
//    }

//    public Employee createEmployee(Employee employee) {
//        em = JPAUtil.getEntityManager();
//        em.getTransaction().begin();
//        em.persist(employee);
//        em.getTransaction().commit();
//
//        return employee;
//    }

    // LOGIC FOR ENDPOINT 2
    @Transactional
    public Employee findEmpRecordById(Long empNo) {
        em = JPAUtil.getEntityManager();
        return em.find(Employee.class, empNo);

    }

    // LOGIC FOR ENDPOINT 3
    @Transactional
    public List<EmployeeDTO> findEmpDTOByDeptNo(String deptNo, int pageNo) {
        em = JPAUtil.getEntityManager();

        int start = (pageNo - 1) * PAGE_SIZE;

        return em.createNamedQuery("Employee.findEmployeeInDepartment", EmployeeDTO.class)
                .setParameter("deptNo", deptNo)
                .setFirstResult(start)
                .setMaxResults(PAGE_SIZE)
                .getResultList();
    }

    // LOGIC FOR ENDPOINT 4
    public void promoteEmployee(List<PromotionRequestDTO> promotionRequestDTOS) {

        em = JPAUtil.getEntityManager();

        em.getTransaction().begin();
        try {
            for (PromotionRequestDTO promotionRequestDTO : promotionRequestDTOS) {
                LocalDate today = LocalDate.now();
                LocalDate endDate = today.minusDays(1);

                Employee employee = findEmpRecordById(promotionRequestDTO.getEmpNo());
                String currentDeptNo = findLatestDeptEmployee(promotionRequestDTO.getEmpNo()).getDeptNo();
                String targetDeptNo = promotionRequestDTO.getNewDeptNo() != null
                        ? promotionRequestDTO.getNewDeptNo()
                        : currentDeptNo;

                // update titles table
                updateAndInsertNewTitle(employee, promotionRequestDTO.getNewTitle(), today, endDate);
                System.out.println("success 1");

                // update salaries table
                if (promotionRequestDTO.getNewSalary() != null) {
                    updateAndInsertNewSalary(employee, promotionRequestDTO.getNewSalary(), today, endDate);
                    System.out.println("success 2");
                }
                // update dept_emp table if moved to new dept
                if (promotionRequestDTO.getNewDeptNo() != null) {
                    updateAndInsertNewDeptEmp(employee, targetDeptNo, today, endDate);
                    System.out.println("success 3");
                }

                // update dept_manager if promoted to manager
                if (promotionRequestDTO.isManager()) {
                    updateAndInsertNewDeptManager(employee, targetDeptNo, today, endDate);
                    System.out.println("success 4");
                }
            }

            System.out.println("success 5");
            em.getTransaction().commit();
            System.out.println("success 6");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // HELPER METHOD
    private void updateAndInsertNewDeptManager(Employee employee, String deptNo, LocalDate today, LocalDate endDate) {

        DeptManager deptManager = findLatestDeptManager(deptNo);
        System.out.println("old: " + deptManager);

        if (deptManager != null) {
            deptManager.setToDate(endDate);
        }
        System.out.println("new: " + deptManager);

        try {
            DeptManager newDeptManagerObj = new DeptManager();
            Department targetDept = em.find(Department.class, deptNo);

            newDeptManagerObj.setDeptManagerId(new DeptManagerId(employee.getEmpNo(), deptNo));
            newDeptManagerObj.setDeptManDepartmentObj(targetDept);
            newDeptManagerObj.setDeptManEmployeeObj(employee);
            newDeptManagerObj.setFromDate(today);
            newDeptManagerObj.setToDate(LocalDate.of(9999, 1, 1));

            System.out.println(newDeptManagerObj);
            em.persist(newDeptManagerObj);
        } catch (Exception e) {
            String errorMessage = "Failed to save DeptManager record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        }
    }

    private void updateAndInsertNewDeptEmp(Employee employee, String newDeptNo, LocalDate today, LocalDate endDate) {

        DeptEmployee latestDeptEmployee = findLatestDeptEmployee(employee.getEmpNo());
        System.out.println("old: " + latestDeptEmployee);

        if (latestDeptEmployee != null) {
            latestDeptEmployee.setToDate(endDate);
        }
        System.out.println("new: " + latestDeptEmployee);

        try {
            DeptEmployee newDeptEmployeeObj = new DeptEmployee();
            Department targetDept = em.find(Department.class, newDeptNo);

            newDeptEmployeeObj.setDeptEmployeeId(new DeptEmployeeId(employee.getEmpNo(), newDeptNo));
            newDeptEmployeeObj.setDeptEmpDepartmentObj(targetDept);
            newDeptEmployeeObj.setDeptEmpEmployeeObj(employee);
            newDeptEmployeeObj.setFromDate(today);
            newDeptEmployeeObj.setToDate(LocalDate.of(9999, 1, 1));

            System.out.println(newDeptEmployeeObj);
            em.persist(newDeptEmployeeObj);
        } catch (Exception e) {
            String errorMessage = "Failed to save DeptEmployee record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        }
    }

    private void updateAndInsertNewSalary(Employee employee, BigDecimal newSalary, LocalDate today, LocalDate endDate) {

        Salaries latestSalary = findLatestSalary(employee.getEmpNo());
        System.out.println("old: " + latestSalary);

        if (latestSalary != null) {
            latestSalary.setToDate(endDate);
        }
        System.out.println("new: " + latestSalary);

        try {
            Salaries newSalariesObj = new Salaries();
            newSalariesObj.setSalariesId(new SalariesId(employee.getEmpNo(), today));
            newSalariesObj.setEmployee(employee);
            newSalariesObj.setSalary(newSalary);
            newSalariesObj.setToDate(LocalDate.of(9999, 1, 1));

            System.out.println(newSalariesObj);
            em.persist(newSalariesObj);
        } catch (Exception e) {
            String errorMessage = "Failed to save Salary record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        }
    }

    private void updateAndInsertNewTitle(Employee employee, String newTitle, LocalDate today, LocalDate endDate) {

        Titles latestTitle = findLatestTitle(employee.getEmpNo());
        System.out.println("old: " + latestTitle);

        if (latestTitle != null) {
            latestTitle.setToDate(endDate);
        }
        System.out.println("new: " + latestTitle);

        try {
            Titles newTitlesObj = new Titles();
            newTitlesObj.setTitlesId(new TitlesId(employee.getEmpNo(), newTitle, today));
            newTitlesObj.setEmployee(employee);
            newTitlesObj.setToDate(LocalDate.of(9999, 1, 1));

            System.out.println(newTitlesObj);
            em.persist(newTitlesObj);
        } catch (Exception e) {
            String errorMessage = "Failed to save Title record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        }
    }

    // HELPER METHOD
    public Titles findLatestTitle(Long empNo) {
        return em.createNamedQuery("Titles.findLatestEmployeeTitle", Titles.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Salaries findLatestSalary(Long empNo) {
        return em.createNamedQuery("Salaries.findLatestEmployeeSalary", Salaries.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }

    private DeptEmployee findLatestDeptEmployee(Long empNo) {
        return em.createNamedQuery("DeptEmployee.findLatestDeptEmployeeRecord", DeptEmployee.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }

    private DeptManager findLatestDeptManager(String deptNo) {
        return em.createNamedQuery("DeptManager.findLatestDeptManagerRecord", DeptManager.class)
                .setParameter("deptNo", deptNo)
                .setMaxResults(1)
                .getSingleResult();
    }
}
