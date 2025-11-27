package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
//@Getter
//@Setter
//@ToString
public class Salaries {
    @Id
    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "salary")
    private BigDecimal salary;

//    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * Default empty class constructor
     */
    public Salaries() {}
    public Salaries(Long empNo, BigDecimal salary, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.salary = salary;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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
