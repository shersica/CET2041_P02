package entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeptManager {
    @EmbeddedId
    private DeptManagerId deptManagerId;

    @ManyToOne
    @MapsId("dept_no")
    @JoinColumn(name = "dept_no")
    @ToString.Exclude
    private Department deptManDepartmentObj;

    @ManyToOne
    @MapsId("emp_no")
    @JoinColumn(name = "emp_no")
    @ToString.Exclude
    private Employee deptManEmployeeObj;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
