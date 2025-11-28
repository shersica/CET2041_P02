package entities;

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
public class Employee {

    @Id
    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    // one-to-many relationship between employees and dept_emp table
    @OneToMany(mappedBy = "deptEmpEmployeeObj", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DeptEmployee> deptEmployees;

    // one-to-many relationship between employees and dept_manager table
    @OneToMany(mappedBy = "deptManEmployeeObj", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<DeptManager> deptManagers;

    // one-to-many relationship between employees and salaries table
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Salaries> salaries;

    // one-to-many relationship between employees and titles table
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Titles> titles;
}
