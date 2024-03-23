package enjoying.dto.request;

import enjoying.validation.experience.ExperienceValidation;
import lombok.Builder;

import java.math.BigDecimal;
@Builder

public record AddMoneyRequest(
        @ExperienceValidation
        int money) {
}
