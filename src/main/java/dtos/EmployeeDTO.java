package dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object representing an employee details to be displayed when
 * queried by department number. Contains the employee identifier (employee number),
 * employee first and last name and the date they are hired.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    /**
     * Employee identifier, i.e. employee number
     */
    private long empNo;

    /**
     * Employee first name
     */
    private String firstName;

    /**
     * Employee last name
     */
    private String lastName;

    /**
     * Employee hired date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
}
