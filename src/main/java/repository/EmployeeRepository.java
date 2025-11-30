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

//        em = JPAUtil.getEntityManager();

//        em.getTransaction().begin();
        try {
            for (PromotionRequestDTO promotionRequestDTO : promotionRequestDTOS) {
                LocalDate today = LocalDate.now();
                LocalDate endDate = today.minusDays(1);

                Employee employee = findEmpRecordById(promotionRequestDTO.getEmpNo());
                String currentDeptNo = findLatestDeptEmployee(em, promotionRequestDTO.getEmpNo()).getDeptNo();
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
//            em.getTransaction().commit();
            System.out.println("success 6");
        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }

            throw new RuntimeException(e.getMessage(), e);
        } finally {
//            if (em.isOpen()) {
//                em.close();
//            }
        }
    }

    // HELPER METHOD
    private void updateAndInsertNewDeptManager(Employee employee, String deptNo, LocalDate today, LocalDate endDate) {

        EntityManager helperEm = JPAUtil.getEntityManager();
        System.out.println("4");
        helperEm.getTransaction().begin();
        try {
        DeptManager deptManager = findLatestDeptManager(helperEm, deptNo);

        if (deptManager != null) {
            deptManager.setToDate(endDate);
//            helperEm.merge(deptManager);
        }

//        try {
            DeptManager newDeptManagerObj = new DeptManager();
            Department targetDept = helperEm.find(Department.class, deptNo);

            newDeptManagerObj.setDeptManagerId(new DeptManagerId(employee.getEmpNo(), deptNo));
            newDeptManagerObj.setDeptManDepartmentObj(targetDept);
            newDeptManagerObj.setDeptManEmployeeObj(employee);
            newDeptManagerObj.setFromDate(today);
            newDeptManagerObj.setToDate(LocalDate.of(9999, 1, 1));

            helperEm.persist(newDeptManagerObj);
            helperEm.getTransaction().commit();
        } catch (Exception e) {
            if (helperEm.getTransaction().isActive()) {
                helperEm.getTransaction().rollback();
            }
            String errorMessage = "Failed to save DeptManager record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
            if (helperEm.isOpen()) {
                helperEm.close();
            }
        }
    }

    private void updateAndInsertNewDeptEmp(Employee employee, String newDeptNo, LocalDate today, LocalDate endDate) {

        EntityManager helperEm = JPAUtil.getEntityManager();
        System.out.println("3");
        helperEm.getTransaction().begin();
        try {
        DeptEmployee latestDeptEmployee = findLatestDeptEmployee(helperEm, employee.getEmpNo());

        if (latestDeptEmployee != null) {
            latestDeptEmployee.setToDate(endDate);
//            helperEm.merge(latestDeptEmployee);
        }

//        try {
            DeptEmployee newDeptEmployeeObj = new DeptEmployee();
            Department targetDept = helperEm.find(Department.class, newDeptNo);

            newDeptEmployeeObj.setDeptEmployeeId(new DeptEmployeeId(employee.getEmpNo(), newDeptNo));
            newDeptEmployeeObj.setDeptEmpDepartmentObj(targetDept);
            newDeptEmployeeObj.setDeptEmpEmployeeObj(employee);
            newDeptEmployeeObj.setFromDate(today);
            newDeptEmployeeObj.setToDate(LocalDate.of(9999, 1, 1));

            helperEm.persist(newDeptEmployeeObj);
            helperEm.getTransaction().commit();
        } catch (Exception e) {
            if (helperEm.getTransaction().isActive()) {
                helperEm.getTransaction().rollback();
            }
            String errorMessage = "Failed to save DeptEmployee record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
            if (helperEm.isOpen()) {
                helperEm.close();
            }
        }
    }

    private void updateAndInsertNewSalary(Employee employee, BigDecimal newSalary, LocalDate today, LocalDate endDate) {

        EntityManager helperEm = JPAUtil.getEntityManager();
        System.out.println("2");
        helperEm.getTransaction().begin();
        try {
        Salaries latestSalary = findLatestSalary(helperEm, employee.getEmpNo());

        if (latestSalary != null) {
            latestSalary.setToDate(endDate);
            helperEm.merge(latestSalary);
        }

//        try {
            Salaries newSalariesObj = new Salaries();
            newSalariesObj.setSalariesId(new SalariesId(employee.getEmpNo(), today));
            newSalariesObj.setEmployee(employee);
            newSalariesObj.setSalary(newSalary);
            newSalariesObj.setToDate(LocalDate.of(9999, 1, 1));

            helperEm.persist(newSalariesObj);
            helperEm.getTransaction().commit();
        } catch (Exception e) {
            if (helperEm.getTransaction().isActive()) {
                helperEm.getTransaction().rollback();
            }
            String errorMessage = "Failed to save Salary record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
            if (helperEm.isOpen()) {
                helperEm.close();
            }
        }
    }

    private void updateAndInsertNewTitle(Employee employee, String newTitle, LocalDate today, LocalDate endDate) {

        EntityManager helperEm = JPAUtil.getEntityManager();
        System.out.println("1");
        helperEm.getTransaction().begin();
        try {
        Titles latestTitle = findLatestTitle(helperEm, employee.getEmpNo());

        if (latestTitle != null) {
            latestTitle.setToDate(endDate);
            helperEm.merge(latestTitle);
        }

//        try {
            Titles newTitlesObj = new Titles();
            newTitlesObj.setTitlesId(new TitlesId(employee.getEmpNo(), newTitle, today));
            newTitlesObj.setEmployee(employee);
            newTitlesObj.setToDate(LocalDate.of(9999, 1, 1));

            helperEm.persist(newTitlesObj);
            helperEm.getTransaction().commit();
        } catch (Exception e) {
            if (helperEm.getTransaction().isActive()) {
                helperEm.getTransaction().rollback();
            }
            String errorMessage = "Failed to save Title record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
            if (helperEm.isOpen()) {
                helperEm.close();
            }
        }
    }

    // HELPER METHOD
    public Titles findLatestTitle(EntityManager em, Long empNo) {
        return em.createNamedQuery("Titles.findLatestEmployeeTitle", Titles.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Salaries findLatestSalary(EntityManager em, Long empNo) {
        return em.createNamedQuery("Salaries.findLatestEmployeeSalary", Salaries.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }

    private DeptEmployee findLatestDeptEmployee(EntityManager em, Long empNo) {
        return em.createNamedQuery("DeptEmployee.findLatestDeptEmployeeRecord", DeptEmployee.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }

    private DeptManager findLatestDeptManager(EntityManager em, String deptNo) {
        return em.createNamedQuery("DeptManager.findLatestDeptManagerRecord", DeptManager.class)
                .setParameter("deptNo", deptNo)
                .setMaxResults(1)
                .getSingleResult();
    }
}
