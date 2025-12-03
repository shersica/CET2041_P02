package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Employee entity with DB table name as employees
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Employee.findEmployeeInDepartment",
                query = """
                            SELECT DISTINCT NEW dtos.EmployeeDTO(e.empNo, e.firstName, e.lastName, e.hireDate)
                            FROM DeptEmployee de
                            JOIN de.deptEmpEmployeeObj e
                            WHERE de.deptEmpDepartmentObj.deptNo = :deptNo
                        """)

})
public class Employee {
    /**
     * Employee number (Primary key)
     */
    @Id
    @Column(name = "emp_no",  nullable = false)
    private Long empNo;

    /**
     * Birth date of employee
     */
    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * First name of the employee
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Last name of the employee
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Gender of the employee
     */
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Hire date of the employee
     */
    @Column(name = "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    /**
     * Departments the employee have been part of
     */
    @OneToMany(mappedBy = "deptEmpEmployeeObj", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DeptEmployee> deptEmployees;

    /**
     * Departments where the employee is a manager
     */
    @OneToMany(mappedBy = "deptManEmployeeObj", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DeptManager> deptManagers;

    /**
     * Salaries of the employee
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Salaries> salaries;

    /**
     * Titles of the employee
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Titles> titles;
}
