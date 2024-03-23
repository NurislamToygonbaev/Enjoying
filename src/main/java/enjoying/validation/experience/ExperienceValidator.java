package enjoying.validation.experience;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ExperienceValidator implements ConstraintValidator<ExperienceValidation, Integer> {


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value > 0;
    }
}
