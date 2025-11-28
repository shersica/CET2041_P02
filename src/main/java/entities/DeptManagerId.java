package entities;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeptManagerId implements Serializable {
    private Long emp_no;
    private String dept_no;
}
