package entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a manager’s assignment to a department over a date range.
 *
 *  <p>This is a join entity for the many-to-many relationship between
 *  {@link Employee} and {@link Department}, enriched with temporal field
 *  {@code from_date} and {@code to_date}.</p>
 *
 *  <p>Primary Key: {@code deptManagerId}</p>
 *
 *  <p><b>Important:</b> This entity uses {@code @MapsId} to synchronize
 *  the foreign keys with the embedded primary key fields.</p>
 */
@Entity
@Table(name = "dept_manager")
@Getter
@Setter
@ToString(exclude = {"deptManDepartmentObj", "deptManEmployeeObj"})
@NoArgsConstructor
@AllArgsConstructor
public class DeptManager {

    /**
     * Embedded composite primary key linking employee and department.
     */
    @EmbeddedId
    @JsonUnwrapped
    private DeptManagerId deptManagerId;

    /**
     * Department associated with this assignment.
     */
    @ManyToOne
    @MapsId("dept_no")
    @JoinColumn(name = "dept_no")
    @JsonManagedReference
    @JsonUnwrapped
    private Department deptManDepartmentObj;

    /**
     * Employee associated with this assignment
     */
    @ManyToOne
    @MapsId("emp_no")
    @JoinColumn(name = "emp_no")
    @JsonBackReference
    private Employee deptManEmployeeObj;

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
