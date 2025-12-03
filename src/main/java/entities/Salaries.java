package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an employee's salary record over a date range.
 *
 *  <p>This entity links to the {@link Employee} entity,
 *  enriched with temporal field {@code salary} and {@code toDate}.</p>
 *
 *  <p>Primary Key: {@code salariesId}</p>
 *
 *  <p><b>Important:</b> This entity uses {@code @MapsId} to synchronize
 *  the foreign keys with the embedded primary key fields.</p>
 */
@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Salaries {

    /**
     * Employee salary
     */
    @Column(name = "salary")
    private BigDecimal salary;

    /**
     * Embedded composite primary key linking employee and salaries
     */
    @EmbeddedId
    @JsonUnwrapped
    private SalariesId salariesId;

    /**
     * Date when the assignment ended
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    /**
     * Employee associated with this assignment
     */
    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    @JsonBackReference
    private Employee employee;

}
