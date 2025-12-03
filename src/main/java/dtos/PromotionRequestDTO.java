package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object representing a promotion request submitted
 * through the API. Contains the employee identifier (Employee Number) and the
 * new employment attributes to be applied (title, salary, department number
 * and if the employee is promoted to a manager status).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionRequestDTO {

    /**
     * Employee identifier, i.e. employee number. Must not be null.
     */
    @NotNull
    private Long empNo;

    /**
     * New title to be assigned. Optional.
     */
    private String newTitle;

    /**
     * New salary to be assigned. Must be a positive number.
     */
    private BigDecimal newSalary;

    /**
     * New department number to be assigned. Optional.
     */
    private String newDeptNo;

    /**
     * Set to true if the employee is assigned as a manager.
     * False otherwise.
     */
    @JsonProperty("isManager")
    private boolean isManager;
}
