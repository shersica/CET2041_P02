package entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "titles")
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString(exclude = "employee")
@JsonPropertyOrder({"title", "fromDate", "toDate"})
@NamedQueries({
        @NamedQuery(name = "Titles.findLatestEmployeeTitle",
        query = "SELECT t FROM Titles t " +
                "WHERE t.employee.empNo = :empNo " +
                "ORDER BY t.titlesId.fromDate DESC")
})
public class Titles {

    @EmbeddedId
    @JsonIgnore
    private TitlesId titlesId;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonBackReference
    private Employee employee;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    // custom getter
    @JsonProperty("title")
    public String getTitle() {
        return this.titlesId.getTitle();
    }

    @JsonProperty("fromDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() {
        return this.titlesId.getFromDate();
    }

}
