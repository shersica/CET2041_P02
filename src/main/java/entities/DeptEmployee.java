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
//@AllArgsConstructor
@JsonPropertyOrder({"deptName", "deptNo", "fromDate", "toDate"})
public class DeptEmployee {

    @EmbeddedId
    @JsonIgnore
    private DeptEmployeeId deptEmployeeId;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonBackReference
    private Employee deptEmpEmployeeObj;

    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no")
    @JsonBackReference
    private Department deptEmpDepartmentObj;

    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    // custom getter
    @JsonProperty("deptNo")
    public String getDeptNo() {
        return this.deptEmployeeId.getDeptNo();
    }

    @JsonProperty("deptName")
    public String getDeptName() {
        return this.deptEmpDepartmentObj.getDeptName();
    }
}
