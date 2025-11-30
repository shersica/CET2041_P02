package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NamedQueries({
        @NamedQuery(name = "Department.findAllDepartments",
        query = "SELECT e FROM Department e " +
                "ORDER BY e.deptNo")
})
public class Department {

    @Id
    @Column(name = "dept_no")
    private String deptNo;

    @Column(name = "dept_name")
    private String deptName;

    // one-to-many relationship between departments and dept_emp table
    @OneToMany(mappedBy = "deptEmpDepartmentObj")
    @JsonIgnore
    private List<DeptEmployee> deptEmployees;

    // one-to-many relationship between departments and dept_manager table
    @OneToMany(mappedBy = "deptManDepartmentObj")
    @JsonIgnore
    private List<DeptManager> deptManagers;

}
