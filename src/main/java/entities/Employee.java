package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an employee record in the {@code Employees} table.
 *
 *  <p>This entity stores the basic personal information of an employee and
 *  serves as the root entity for several one-to-many relationships such as
 *  titles, salaries, and department assignments.</p>
 *
 *  <p>Primary Key: {@code empNo}</p>
 *
 *  <p><b>Relationships:</b></p>
 *  <ul>
 *    <li>{@link DeptEmployee} – historical department assignments</li>
 *    <li>{@link DeptManager} - historical manager assignments</li>
 *    <li>{@link Salaries} – salary history</li>
 *    <li>{@link Titles} – title history</li>
 *  </ul>
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
     * Unique employee identifier (Primary key)
     */
    @Id
    @Column(name = "emp_no",  nullable = false)
    private Long empNo;

    /**
     * Employee date of birth
     */
    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * Employee first name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Employee last name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Employee gender (M/F)
     */
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Employee hired date
     */
    @Column(name = "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    /**
     * Employee historical department assignment
     */
    @OneToMany(mappedBy = "deptEmpEmployeeObj", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DeptEmployee> deptEmployees;

    /**
     * Employee historical manager assignment
     */
    @OneToMany(mappedBy = "deptManEmployeeObj", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DeptManager> deptManagers;

    /**
     * Employee salary history
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Salaries> salaries;

    /**
     * Employee title history
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Titles> titles;
}
