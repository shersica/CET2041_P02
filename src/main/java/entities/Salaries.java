package entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "employee")
public class Salaries {

    @EmbeddedId
    private SalariesId salariesId;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

}
