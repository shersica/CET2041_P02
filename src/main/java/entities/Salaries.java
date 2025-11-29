package entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = "employee")
@JsonPropertyOrder({"salary", "fromDate", "toDate"})
public class Salaries {

    @EmbeddedId
    @JsonIgnore
    private SalariesId salariesId;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonBackReference
    private Employee employee;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    //custom getter
    @JsonProperty("fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() {
        return this.salariesId.getFromDate();
    }

}
