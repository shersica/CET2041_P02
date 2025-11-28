package entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "dept_no")
    private String deptNo;

    @Column(name = "dept_name")
    private String deptName;

    // one-to-many relationship between departments and dept_emp table
    @OneToMany(mappedBy = "deptEmpDepartmentObj")
    @ToString.Exclude
    private List<DeptEmployee> deptEmployees;

    // one-to-many relationship between departments and dept_manager table
    @OneToMany(mappedBy = "deptManDepartmentObj")
    @ToString.Exclude
    private List<DeptManager> deptManagers;
}
