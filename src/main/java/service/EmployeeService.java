package service;

import dtos.EmployeeDTO;
import dtos.PromotionRequestDTO;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.BadRequestException;
import repository.*;
import util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Service responsible for handling employee-related operations, e.g.
 * finding by ID, by department and promoting employee.
 */
public class EmployeeService {

    /**
     * Repository responsible for accessing and modifying {@link Employee} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final EmployeeRepository employeeRepository = new EmployeeRepository();

    /**
     * Repository responsible for accessing and modifying {@link Titles} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final TitlesRepository titlesRepository = new TitlesRepository();

    /**
     * Repository responsible for accessing and modifying {@link Salaries} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final SalariesRepository salariesRepository = new SalariesRepository();

    /**
     * Repository responsible for accessing and modifying {@link DeptManager} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final DeptManagerRepository deptManagerRepository = new DeptManagerRepository();

    /**
     * Repository responsible for accessing and modifying {@link Department} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final DepartmentRepository departmentRepository = new DepartmentRepository();

    /**
     * Repository responsible for accessing and modifying {@link DeptEmployee} data.
     * <p>
     * This instance is used by the service to perform employee-related database
     * operations such as retrieval, updates, and existence checks.
     */
    private final DeptEmployeeRepository deptEmployeeRepository = new DeptEmployeeRepository();

    /**
     * Find and return Employee by its unique identifier, i.e. employee number
     * @param id employee unique identifier, i.e. employee number
     * @return the Employee if found, otherwise null
     */
    public Employee findById(long id) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return employeeRepository.findById(em, id);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching employee: " + e.getMessage());
        }
    }

    /**
     * Find and return a list of Employee DTO by unique department identifier
     * and page number.
     * @param deptNo department unique identifier, i.e. employee number
     * @param page page number to be displayed
     * @return the list of Employee DTO according to the page number
     */
    public List<EmployeeDTO> findEmployeesByDept(String deptNo, int page) {
        if(page < 1){
            page = 1;
        }
        try (EntityManager em = JPAUtil.getEntityManager()) {
            Department dept = departmentRepository.findDepartmentById(em, deptNo);
            if (dept == null) {
                throw new BadRequestException("Invalid deptNo. Department not found");
            }
            return employeeRepository.findEmployeesByDept(em, deptNo, page);
        }
    }

    /**
     * Promotes an employee based on the given request and applies
     * changes atomically. If any validation or persistence step fails,
     * the entire transaction is rolled back.
     * @param promotionRequestDTO request data containing the desired promotion attributes
     */
    public void promoteEmployee(PromotionRequestDTO promotionRequestDTO) {
        if (promotionRequestDTO == null) {
            throw new BadRequestException("Promotion request body is null");
        }

        Long empNo = promotionRequestDTO.getEmpNo();
        String newTitle = toTitleCase(promotionRequestDTO.getNewTitle());
        BigDecimal newSalary = promotionRequestDTO.getNewSalary();
        String newDeptNo = promotionRequestDTO.getNewDeptNo();
        boolean isManager = promotionRequestDTO.isManager();

        if (newTitle == null || newTitle.isEmpty()) {
            throw new BadRequestException("Title cannot be null or empty");
        }
        if (newSalary == null || newSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Salary cannot be null or negative");
        }
        if (newSalary.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) > 0) {
            throw new BadRequestException("Salary is too large");
        }
        if (newDeptNo == null || newDeptNo.isEmpty()) {
            throw new BadRequestException("DeptNo cannot be null");
        }
        if(newDeptNo.length() != 4){
            throw new BadRequestException("Invalid DeptNo: Must be char length of 4");
        }

        EntityManager em = JPAUtil.getEntityManager();
        LocalDate fromDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(9999, 1, 1);

        try {
            em.getTransaction().begin();

            Employee employee = employeeRepository.findById(em, empNo);
            if (employee == null) {
                throw new BadRequestException("Invalid empNo. Employee not found");
            }

            Department dept = departmentRepository.findDepartmentById(em, newDeptNo);
            if (dept == null) {
                throw new BadRequestException("Invalid deptNo. Department not found");
            }

            // ---------- TITLES ----------
            List<Titles> titles = employee.getTitles();
            //get curr active title
            Titles currTitle = null;
            for(Titles title : titles){
                if(endDate.equals(title.getToDate())){
                    currTitle = title;
                }
            }
            if(currTitle != null && currTitle.getTitlesId().getTitle().equals(newTitle)){
                System.out.println("New title is the same as current active title. No update needed");
            } else {
                //update current active title date
                if (currTitle != null) {
                    currTitle.setToDate(fromDate);
//                    em.flush();
                }

                // Insert new title only if the composite PK doesn't already exist
                TitlesId newTitlesId = new TitlesId(empNo, newTitle, fromDate);
                Titles existingTitles = titlesRepository.findById(em, newTitlesId);
                if (existingTitles == null) {
                    Titles newTitles = new Titles(newTitlesId, endDate, employee);
                    titlesRepository.addTitle(em, newTitles);
//                    em.flush();
                } else {
                    System.out.println("A title row with the same composite PK already exists. Skipping insert.");
                }
            }

            // ---------- SALARIES ----------
            List<Salaries> salaries = employee.getSalaries();
            Salaries currSalary = null;
            for(Salaries oldSalary : salaries){
                if(oldSalary.getToDate().equals(endDate)){
                    currSalary = oldSalary;
                    break;
                }
            }

            boolean salaryChanged = true;
            if (currSalary != null) {
                salaryChanged = !currSalary.getSalary().equals(newSalary);
            }

            //Only update salary history if value actually changed
            if (salaryChanged) {
                // close old active salary record if there is an active record
                if(currSalary != null) {
                    currSalary.setToDate(fromDate);
                }
//                em.flush();

                // ensure this specific update hasn't already happened today
                SalariesId newSalaryId = new SalariesId(empNo, fromDate);
                Salaries existingSalary = salariesRepository.findById(em, newSalaryId);
                if (existingSalary != null) {
                    throw new BadRequestException("Salary already updated today");
                }

                // insert new salary row
                Salaries newSalaryEntity = new Salaries(newSalary, newSalaryId, endDate, employee);
                salariesRepository.addSalary(em, newSalaryEntity);
//                em.flush();
            }

            // ---------- DEPT MANAGER ----------
            List<DeptManager> deptManagers = employee.getDeptManagers();
            if (isManager) {
                boolean alreadyManagerInTargetDept = false;
                for (DeptManager dm : deptManagers) {
                    if (endDate.equals(dm.getToDate())) {
                        String currentDeptNo = dm.getDeptManDepartmentObj().getDeptNo();
                        if (!currentDeptNo.equals(newDeptNo)) {
                            // close the old manager appointment
                            dm.setToDate(fromDate);
//                            em.merge(dm);
//                            em.flush();
                        } else {
                            // already active manager in that dept
                            alreadyManagerInTargetDept = true;
                        }
                    }
                }

                //emp is currently not a manager in the currdept
                if (!alreadyManagerInTargetDept) {
                    DeptManagerId newDmId = new DeptManagerId(empNo, newDeptNo);
                    DeptManager existingDm = deptManagerRepository.findById(em, newDmId);
                    if (existingDm == null) {
                        DeptManager newDm = new DeptManager(newDmId, dept, employee, fromDate, endDate);
                        deptManagerRepository.addDeptManager(em, newDm);
//                        em.flush();
                    }
                }
            } else {
                // Demotion: close all active manager records
                for (DeptManager dm : deptManagers) {
                    if (endDate.equals(dm.getToDate())) {
                        dm.setToDate(fromDate);
//                        em.flush();
                    }
                }
            }
            // ---------- DEPT EMPLOYEE ----------
            List<DeptEmployee> deptEmployees = employee.getDeptEmployees();
            DeptEmployee currDept = null;
            boolean isPreviousDept = false;

            // Find current active department and check if newDept is a previous closed dept
            for (DeptEmployee de : deptEmployees) {
                if (de.getToDate().equals(endDate)) {
                    currDept = de;
                } else if (de.getDeptEmpDepartmentObj().getDeptNo().equalsIgnoreCase(newDeptNo)) {
                    isPreviousDept = true;
                }
            }

            // Cannot move to a previous department
            if (isPreviousDept) {
                throw new BadRequestException("Cannot reassign employee to a previous department: " + newDeptNo);
            }

            // If current active dept is same as newDept, do nothing
            if (currDept != null && currDept.getDeptEmpDepartmentObj().getDeptNo().equalsIgnoreCase(newDeptNo)) {
                // already in target dept, nothing to update
            } else {
                // Close current active department if it exists
                if (currDept != null) {
                    currDept.setToDate(fromDate);
//                    em.flush();
                }

                // Insert new DeptEmployee record
                DeptEmployeeId newDeId = new DeptEmployeeId(empNo, newDeptNo);
                DeptEmployee newDe = new DeptEmployee(newDeId, employee, dept, fromDate, endDate);
                deptEmployeeRepository.addDeptEmployee(em, newDe);
//                em.flush();
            }

            em.getTransaction().commit();
        } catch (BadRequestException bre) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw bre;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to promote employee: " + e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }


    /**
     * Convert a string to title-case, i.e. This Title
     * @param title the title to be converted
     * @return title in title-case
     */
    public String toTitleCase(String title){
        if(title == null || title.isEmpty()){
            return null;
        }
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }
}
