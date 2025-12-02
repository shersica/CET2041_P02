package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionRequestDTO {
    @NotNull
    private Long empNo;
    @NotBlank
    private String newTitle;
    private BigDecimal newSalary;
    private String newDeptNo;
    @JsonProperty("isManager")
    private boolean isManager;
}
