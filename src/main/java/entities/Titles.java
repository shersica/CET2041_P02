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
@Table(name = "titles")
//@Getter
//@Setter
//@ToString
public class Titles {
    @Id
    @Column(name = "emp_no")
    private Long empNo;

//    @Id
    @Column(name = "title")
    private String title;

//        @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

//        @Id
    @Column(name = "to_date")
    private LocalDate toDate;

    public Titles() {}
    public Titles(Long empNo, String title, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
