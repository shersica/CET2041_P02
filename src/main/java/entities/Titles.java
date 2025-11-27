package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "titles")
@Getter
@Setter
@ToString(exclude = "employee")
public class Titles {

    @Column(name = "to_date")
    private LocalDate toDate;

    @EmbeddedId
    private TitlesId titlesId;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

}
