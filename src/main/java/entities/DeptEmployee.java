package entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents an employee’s assignment to a department over a date range.
 *
 *  <p>This is a join entity for the many-to-many relationship between
 *  {@link Employee} and {@link Department}, enriched with temporal field
 *  {@code from_date} and {@code to_date}.</p>
 *
 *  <p>Primary Key: {@code DeptEmployeeId}</p>
 *
 *  <p><b>Important:</b> This entity uses {@code @MapsId} to synchronize
 *  the foreign keys with the embedded primary key fields.</p>
 */
@Entity
@Table(name = "dept_emp")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "DeptEmployee.findLatestDeptEmployeeRecord",
        query = "SELECT de FROM DeptEmployee de " +
                "WHERE de.deptEmployeeId.emp_no = :empNo " +
                "ORDER BY de.fromDate DESC ")
public class DeptEmployee {

    /**
     * Embedded composite primary key linking employee and department
     */
    @EmbeddedId
    @JsonUnwrapped
    private DeptEmployeeId deptEmployeeId;

    /**
     * Employee associated with this assignment
     */
    @ManyToOne
    @MapsId("emp_no")
    @JoinColumn(name = "emp_no")
    @JsonBackReference
    private Employee deptEmpEmployeeObj;

    /**
     * Department associated with this assignment
     */
    @ManyToOne
    @MapsId("dept_no")
    @JoinColumn(name = "dept_no")
    @JsonManagedReference
    @JsonUnwrapped
    private Department deptEmpDepartmentObj;

    /**
     * Date when the assignment started
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    /**
     * Date when the assignment ended
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;
}
