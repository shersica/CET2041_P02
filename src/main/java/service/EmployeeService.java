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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeService {
    private final EmployeeRepository employeeRepository = new EmployeeRepository();
    private final TitlesRepository titlesRepository = new TitlesRepository();
    private final SalariesRepository salariesRepository = new SalariesRepository();
    private final DeptManagerRepository deptManagerRepository = new DeptManagerRepository();
    private final DepartmentRepository departmentRepository = new DepartmentRepository();
    private final DeptEmployeeRepository deptEmployeeRepository = new DeptEmployeeRepository();


    public Employee findById(long id) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return employeeRepository.findById(em, id);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching employee: " + e.getMessage());
        }
    }

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

    public void promoteEmployee(PromotionRequestDTO promotionRequestDTO) {
        if (promotionRequestDTO == null) {
            throw new BadRequestException("Promotion request body is null");
        }

        Long empNo = promotionRequestDTO.getEmpNo();
        String newTitle = promotionRequestDTO.getNewTitle();
        BigDecimal newSalary = promotionRequestDTO.getNewSalary();
        String newDeptNo = promotionRequestDTO.getNewDeptNo();
        boolean isManager = promotionRequestDTO.isManager();

        if (newTitle == null || newTitle.isEmpty()) {
            throw new BadRequestException("Title cannot be null or empty");
        }
        if (newSalary == null || newSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Salary cannot be null or negative");
        }
        if (newDeptNo == null || newDeptNo.isEmpty()) {
            throw new BadRequestException("DeptNo cannot be null");
        }
        if(isValidTitle(newTitle)) {
            newTitle = toTitleCase(newTitle);
        } else {
            throw new BadRequestException("Invalid title");
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
            //get currActiveTitle
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
                    em.flush();
                }

                // Insert new title only if the composite PK doesn't already exist
                TitlesId newTitlesId = new TitlesId(empNo, newTitle, fromDate);
                Titles existingTitles = titlesRepository.findById(em, newTitlesId);
                if (existingTitles == null) {
                    Titles newTitles = new Titles(newTitlesId, endDate, employee);
                    titlesRepository.addTitle(em, newTitles);
                    em.flush();
                } else {
                    System.out.println("A title row with the same composite PK already exists. Skipping insert.");
                }
            }

            // ---------- SALARIES ----------
            List<Salaries> salaries = employee.getSalaries();
            for (Salaries oldSalary : salaries) {
                if (endDate.equals(oldSalary.getToDate()) && oldSalary.getSalary().compareTo(newSalary) != 0) {
                    oldSalary.setToDate(fromDate);
//                    em.merge(oldSalary);
                    em.flush();
                }
            }

            SalariesId newSalaryId = new SalariesId(empNo, fromDate);
            Salaries existingSalary = salariesRepository.findById(em, newSalaryId);
            if(existingSalary != null){
                throw new BadRequestException("Salary already updated today");
            } else {
                Salaries newSalaryEntity = new Salaries(newSalary, newSalaryId, endDate, employee);
                salariesRepository.addSalary(em, newSalaryEntity);
                em.flush();
            }

            // ---------- DEPT MANAGER ----------
            if (isManager) {
                List<DeptManager> deptManagers = employee.getDeptManagers();
                boolean alreadyManagerInTargetDept = false;

                for (DeptManager dm : deptManagers) {
                    if (endDate.equals(dm.getToDate())) {
                        String currentDeptNo = dm.getDeptManDepartmentObj().getDeptNo();
                        if (!currentDeptNo.equals(newDeptNo)) {
                            // close the old manager appointment
                            dm.setToDate(fromDate);
//                            em.merge(dm);
                            em.flush();
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
                        em.flush();
                    }
                }
            }

            // ---------- DEPT EMPLOYEE ----------
            List<DeptEmployee> deptEmployees = employee.getDeptEmployees();
            boolean alreadyInTargetDept = false;
            for (DeptEmployee de : deptEmployees) {
                if (endDate.equals(de.getToDate())) {
                    String currentDeptNo = de.getDeptEmpDepartmentObj().getDeptNo();
                    if (!currentDeptNo.equals(newDeptNo)) {
                        de.setToDate(fromDate);
//                        em.merge(de);
                        em.flush();
                    } else {
                        alreadyInTargetDept = true;
                    }
                }
            }

            if (!alreadyInTargetDept) {
                DeptEmployeeId newDeId = new DeptEmployeeId(empNo, newDeptNo);
                DeptEmployee existingDe = deptEmployeeRepository.findById(em, newDeId);
                if (existingDe == null) {
                    DeptEmployee newDe = new DeptEmployee(newDeId, employee, dept, fromDate, endDate);
                    deptEmployeeRepository.addDeptEmployee(em, newDe);
                    em.flush();
                }
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

    public boolean isValidTitle(String title){
        Pattern pattern = Pattern.compile("^[A-Za-z ]+$");
        Matcher matcher = pattern.matcher(title);
        return matcher.find();
    }

    public String toTitleCase(String title){
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }
}
