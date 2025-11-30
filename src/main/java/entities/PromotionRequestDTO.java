package entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PromotionRequestDTO {

    @NotNull private Long empNo;

    @NotBlank private String newTitle;

    private BigDecimal newSalary;

    private String newDeptNo;

    @JsonProperty("isManager")
    private boolean isManager;

}
