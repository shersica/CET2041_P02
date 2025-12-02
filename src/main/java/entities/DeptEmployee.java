package entities;

import com.fasterxml.jackson.annotation.*;
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
    @JsonUnwrapped
    private DeptEmployeeId deptEmployeeId;

    @ManyToOne
    @MapsId("emp_no")
    @JoinColumn(name = "emp_no")
//    @ToString.Exclude
//    @JsonIgnore
    @JsonBackReference
    private Employee deptEmpEmployeeObj;

    @ManyToOne
    @MapsId("dept_no")
    @JoinColumn(name = "dept_no")
//    @ToString.Exclude
//    @JsonIgnore
    @JsonManagedReference
    @JsonUnwrapped
    private Department deptEmpDepartmentObj;

    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;
}
