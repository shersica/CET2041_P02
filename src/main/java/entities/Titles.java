package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents an employee's title record over a date range.
 *
 *  <p>This entity links to the {@link Employee} entity,
 *  enriched with temporal field {@code toDate}.</p>
 *
 *  <p>Primary Key: {@code titlesId}</p>
 *
 *  <p><b>Important:</b> This entity uses {@code @MapsId} to synchronize
 *  the foreign keys with the embedded primary key fields.</p>
 */
@Entity
@Table(name = "titles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Titles {

    /**
     * Embedded composite primary key linking employee and titles
     */
    @EmbeddedId
    @JsonUnwrapped
    private TitlesId titlesId;

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
