package entities;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalariesId implements Serializable {

    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "from_date")
    private LocalDate fromDate;

}
