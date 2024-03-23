package enjoying.dto.request;

import enjoying.validation.email.EmailValidation;
import enjoying.validation.password.PasswordValidation;
import jakarta.validation.constraints.Email;
import lombok.Builder;


@Builder
public record SignInRequest(

        @EmailValidation @Email
        String email,
        @PasswordValidation
        String password) {

}
