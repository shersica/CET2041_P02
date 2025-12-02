package entities;

import com.fasterxml.jackson.annotation.*;
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
    @JsonUnwrapped
    private DeptManagerId deptManagerId;

    @ManyToOne
    @MapsId("dept_no")
    @JoinColumn(name = "dept_no")
    @ToString.Exclude
//    @JsonIgnore
    @JsonManagedReference
    @JsonUnwrapped
    private Department deptManDepartmentObj;

    @ManyToOne
    @MapsId("emp_no")
    @JoinColumn(name = "emp_no")
    @ToString.Exclude
//    @JsonIgnore
    @JsonBackReference
    private Employee deptManEmployeeObj;

    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;
}
