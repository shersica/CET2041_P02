package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "emp_no")
    private Long empNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hire_date")
    private LocalDate  hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Salaries> salaries;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Titles> titles;

}
