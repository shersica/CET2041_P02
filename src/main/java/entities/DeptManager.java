package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
@Getter
@Setter
@ToString(exclude = {"deptManDepartmentObj", "deptManEmployeeObj"})
@NoArgsConstructor
//@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "DeptManager.findLatestDeptManagerRecord",
                query = "SELECT dm FROM DeptManager dm " +
                        "WHERE dm.deptManagerId.deptNo = :deptNo " +
                        "ORDER BY dm.fromDate DESC "),
})
public class DeptManager {

    @EmbeddedId
    @JsonIgnore
    private DeptManagerId deptManagerId;

    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no",referencedColumnName = "dept_no")
    @JsonBackReference
    private Department deptManDepartmentObj;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonBackReference
    private Employee deptManEmployeeObj;

    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    // custom getter
    @JsonProperty("empNo")
    public Long getEmpNo() {
        return this.deptManagerId.getEmpNo();
    }

}
