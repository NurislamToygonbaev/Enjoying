package enjoying.dto.request;

import enjoying.enums.Role;
import enjoying.validation.dateOfBirth.DateOfBirthValidation;
import enjoying.validation.email.EmailValidation;
import enjoying.validation.experience.ExperienceValidation;
import enjoying.validation.password.PasswordValidation;
import enjoying.validation.phoneNumber.PhoneNumberValidation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class SignUpRequest {
    @NotBlank
    private String fullName;
    @Email @EmailValidation
    private String email;
    @PasswordValidation
    private String password;
    @DateOfBirthValidation
    private LocalDate dateOfBirth;
    @PhoneNumberValidation
    private String phoneNumber;
    @NotBlank
    private String image;

}
