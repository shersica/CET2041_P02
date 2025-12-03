package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a department record in the {@code departments} table.
 *
 *  <p>This entity stores the basic information of the departments in the company
 *  and serves as the root entity for one-to-many relationships such as
 *  employee and manager assignments to the department.</p>
 *
 *  <p>Primary Key: {@code dept_no}</p>
 *
 *  <p><b>Relationships:</b></p>
 *  <ul>
 *    <li>{@link DeptEmployee} – historical employee assignments</li>
 *    <li>{@link DeptManager} - historical manager assignments</li>
 *  </ul>
 */
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

    /**
     * Unique department identifier (primary key)
     */
    @Id
    @Column(name = "dept_no", nullable = false)
    private String deptNo;

    /**
     * Department name
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * Historical employee assignment
     */
    // one-to-many relationship between departments and dept_emp table
    @OneToMany(mappedBy = "deptEmpDepartmentObj")
    @JsonBackReference
    private List<DeptEmployee> deptEmployees;

    /**
     * Historical manager assignment
     */
    // one-to-many relationship between departments and dept_manager table
    @OneToMany(mappedBy = "deptManDepartmentObj")
    @JsonBackReference
    private List<DeptManager> deptManagers;
}
