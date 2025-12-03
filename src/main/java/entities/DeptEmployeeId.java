package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * Composite primary key for {@link DeptEmployee}
 *
 * <p>This key uniquely identifies the relationship between an employee
 * and a department on a given date range. It is used by JPA through
 * {@code @EmbeddedId}</p>
 *
 * <p>Fields: </p>
 * <ul>
 *     <li>{@code emp_no} - Employee unique identifier</li>
 *     <li>{@code dept_no} - Department unique identifier</li>
 * </ul>
 */
@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeptEmployeeId implements Serializable {

    /**
     * Employee unique identifier (part of composite key)
     */
    @JsonIgnore
    private Long emp_no;

    /**
     * Department unique identifier (part of composite key)
     */
    @JsonIgnore
    private String dept_no;
}
