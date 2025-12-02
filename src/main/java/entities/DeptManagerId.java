package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeptManagerId implements Serializable {
    @JsonIgnore
    private Long emp_no;
    @JsonIgnore
    private String dept_no;
}
