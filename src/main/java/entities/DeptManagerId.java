package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeptManagerId implements Serializable {

    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "dept_no")
    private String deptNo;
}
