package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "titles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Titles {

    @EmbeddedId
    @JsonUnwrapped
    private TitlesId titlesId;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
//    @JsonIgnore
    @JsonBackReference
    private Employee employee;

}
