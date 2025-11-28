package entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeptEmployee {
    @EmbeddedId
    private DeptEmployeeId deptEmployeeId;

    @ManyToOne
    @MapsId("emp_no")
    @JoinColumn(name = "emp_no")
    @ToString.Exclude
    private Employee deptEmpEmployeeObj;

    @ManyToOne
    @MapsId("dept_no")
    @JoinColumn(name = "dept_no")
    @ToString.Exclude
    private Department deptEmpDepartmentObj;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
