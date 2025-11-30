package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Employee.findEmployeeInDepartment",
                query = "SELECT NEW entities.EmployeeDTO(e.empNo, e.firstName, e.lastName, e.birthDate) " +
                        "FROM Employee e JOIN DeptEmployee de ON e.empNo = de.deptEmpEmployeeObj.empNo " +
                        "WHERE de.deptEmpDepartmentObj.deptNo = :deptNo " +
                        "ORDER BY e.empNo"),

})
public class Employee {

    @Id
    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "birth_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    // one-to-many relationship between employees and dept_emp table
    @OneToMany(mappedBy = "deptEmpEmployeeObj")
    @JsonManagedReference
//    @JsonIgnore
    private List<DeptEmployee> deptEmployees;

    // one-to-many relationship between employees and dept_manager table
    @OneToMany(mappedBy = "deptManEmployeeObj")
    @JsonManagedReference
//    @JsonIgnore
    private List<DeptManager> deptManagers;

    // one-to-many relationship between employees and salaries table
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
//    @JsonIgnore
    private List<Salaries> salaries;

    // one-to-many relationship between employees and titles table
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
//    @JsonIgnore
    private List<Titles> titles;

}
