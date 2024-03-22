package restaurant.validation.phoneNumber;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import restaurant.validation.password.PasswordValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PhoneNumberValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValidation {
    String message() default "{password must be more 7 symbol}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
