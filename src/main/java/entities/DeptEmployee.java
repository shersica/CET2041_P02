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
@Table(name = "dept_emp")
//@Getter
//@Setter
//@ToString
public class DeptEmployee {
    @Id
    @Column(name = "emp_no")
    private Long empNo;

//    @Id
    @Column(name = "dept_no")
    private String deptNo;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Default class constructor
     */
    public DeptEmployee() {}

    /**
     * Class constructor with all fields
     * @param empNo Employee number
     * @param deptNo Department number
     * @param fromDate From date
     * @param toDate To date
     */
    public DeptEmployee(Long empNo, String deptNo, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.deptNo = deptNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
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
