package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Composite primary key for {@link Titles}
 *
 * <p>This key uniquely identifies the relationship between an employee
 * and their title on a given date range. It is used by JPA through
 * {@code @EmbeddedId}</p>
 *
 * <p>Fields: </p>
 * <ul>
 *     <li>{@code empNo} - employee unique identifier</li>
 *     <li>{@code title} - employee title/position</li>
 *     <li>{@code fromDate} - date when the assignment started</li>
 * </ul>
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitlesId implements Serializable {

    /**
     * Employee unique identifier (part of a composite key)
     */
    @Column(name = "emp_no")
    @JsonIgnore
    private Long empNo;

    /**
     * Employee title/position (part of a composite key)
     */
    @Column(name = "title")
    private String title;

    /**
     * Date when the assignment started (part of a composite key)
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

}
