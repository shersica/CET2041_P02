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

    public Employee findEmpRecordById(EntityManager em, Long empNo) {
//        em = JPAUtil.getEntityManager();
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

        // creation of EntityManager
        em = JPAUtil.getEntityManager();

        // begin transaction
        em.getTransaction().begin();
        try {
            for (PromotionRequestDTO promotionRequestDTO : promotionRequestDTOS) {
                LocalDate today = LocalDate.now();
                LocalDate endDate = today.minusDays(1);

                Employee employee = findEmpRecordById(em, promotionRequestDTO.getEmpNo());
                String currentDeptNo = findLatestDeptEmployee(em, promotionRequestDTO.getEmpNo()).getDeptNo();
                String targetDeptNo = promotionRequestDTO.getNewDeptNo() != null
                        ? promotionRequestDTO.getNewDeptNo()
                        : currentDeptNo;

                // update titles table
                updateAndInsertNewTitle(em, employee, promotionRequestDTO.getNewTitle(), today, endDate);
                System.out.println("success 1");

                // update salaries table
                if (promotionRequestDTO.getNewSalary() != null) {
                    updateAndInsertNewSalary(em, employee, promotionRequestDTO.getNewSalary(), today, endDate);
                    System.out.println("success 2");
                }
                // update dept_emp table if moved to new dept
                if (promotionRequestDTO.getNewDeptNo() != null) {
                    updateAndInsertNewDeptEmp(em, employee, targetDeptNo, today, endDate);
                    System.out.println("success 3");
                }

                // update dept_manager if promoted to manager
                if (promotionRequestDTO.isManager()) {
                    updateAndInsertNewDeptManager(em, employee, targetDeptNo, today, endDate);
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
    private void updateAndInsertNewDeptManager(
            EntityManager em,
            Employee employee,
            String deptNo,
            LocalDate today,
            LocalDate endDate)
    {
        try {
            DeptManager deptManager = findLatestDeptManager(em, deptNo);

            if (deptManager != null) {
                deptManager.setToDate(endDate);
//            helperEm.merge(deptManager);
            }

            DeptManager newDeptManagerObj = new DeptManager();
            Department targetDept = em.find(Department.class, deptNo);

            newDeptManagerObj.setDeptManagerId(new DeptManagerId(employee.getEmpNo(), deptNo));
            newDeptManagerObj.setDeptManDepartmentObj(targetDept);
            newDeptManagerObj.setDeptManEmployeeObj(employee);
            newDeptManagerObj.setFromDate(today);
            newDeptManagerObj.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newDeptManagerObj);
        } catch (Exception e) {
            String errorMessage = "Failed to save DeptManager record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        }
    }

    private void updateAndInsertNewDeptEmp(
            EntityManager em,
            Employee employee,
            String newDeptNo,
            LocalDate today,
            LocalDate endDate)
    {
//        EntityManager helperEm = JPAUtil.getEntityManager();
//        helperEm.getTransaction().begin();
        try {
            DeptEmployee latestDeptEmployee = findLatestDeptEmployee(em, employee.getEmpNo());

            if (latestDeptEmployee != null) {
                latestDeptEmployee.setToDate(endDate);
//            helperEm.merge(latestDeptEmployee);
            }

            DeptEmployee newDeptEmployeeObj = new DeptEmployee();
            Department targetDept = em.find(Department.class, newDeptNo);

            newDeptEmployeeObj.setDeptEmployeeId(new DeptEmployeeId(employee.getEmpNo(), newDeptNo));
            newDeptEmployeeObj.setDeptEmpDepartmentObj(targetDept);
            newDeptEmployeeObj.setDeptEmpEmployeeObj(employee);
            newDeptEmployeeObj.setFromDate(today);
            newDeptEmployeeObj.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newDeptEmployeeObj);
//            em.getTransaction().commit();
        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
            String errorMessage = "Failed to save DeptEmployee record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
//            if (em.isOpen()) {
//                em.close();
//            }
        }
    }

    private void updateAndInsertNewSalary(
            EntityManager em,
            Employee employee,
            BigDecimal newSalary,
            LocalDate today,
            LocalDate endDate)
    {
//        EntityManager helperEm = JPAUtil.getEntityManager();
//        helperEm.getTransaction().begin();
        try {
            Salaries latestSalary = findLatestSalary(em, employee.getEmpNo());

            if (latestSalary != null) {
                latestSalary.setToDate(endDate);
//                em.merge(latestSalary);
            }

            Salaries newSalariesObj = new Salaries();
            newSalariesObj.setSalariesId(new SalariesId(employee.getEmpNo(), today));
            newSalariesObj.setEmployee(employee);
            newSalariesObj.setSalary(newSalary);
            newSalariesObj.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newSalariesObj);
//            em.getTransaction().commit();
        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
            String errorMessage = "Failed to save Salary record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
//            if (em.isOpen()) {
//                em.close();
//            }
        }
    }

    private void updateAndInsertNewTitle(
            EntityManager em,
            Employee employee,
            String newTitle,
            LocalDate today,
            LocalDate endDate)
    {
//        EntityManager helperEm = JPAUtil.getEntityManager();
//        helperEm.getTransaction().begin();
        try {
            Titles latestTitle = findLatestTitle(em, employee.getEmpNo());

            if (latestTitle != null) {
                latestTitle.setToDate(endDate);
//                em.merge(latestTitle);
            }

            Titles newTitlesObj = new Titles();
            newTitlesObj.setTitlesId(new TitlesId(employee.getEmpNo(), newTitle, today));
            newTitlesObj.setEmployee(employee);
            newTitlesObj.setToDate(LocalDate.of(9999, 1, 1));

            em.persist(newTitlesObj);
//            em.getTransaction().commit();
        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
            String errorMessage = "Failed to save Title record: " + e.getMessage();

            throw new RuntimeException(errorMessage, e);
        } finally {
//            if (em.isOpen()) {
//                em.close();
//            }
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
