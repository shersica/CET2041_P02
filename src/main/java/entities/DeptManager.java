package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
//@Getter
//@Setter
//@ToString
public class DeptManager {
    @Id
    @Column(name = "dept_name")
    private String deptName;

//    @Id
    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Default class constructor
     */
    public DeptManager() {}

    /**
     * Class constructor with all fields
     * @param deptName Department name
     * @param empNo Employee number
     * @param fromDate From date
     * @param toDate To date
     */
    public DeptManager(String deptName, Long empNo, LocalDate fromDate, LocalDate toDate) {
        this.deptName = deptName;
        this.empNo = empNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
