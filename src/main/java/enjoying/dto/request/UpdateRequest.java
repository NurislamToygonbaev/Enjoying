package enjoying.dto.request;

import enjoying.validation.dateOfBirth.DateOfBirthValidation;
import enjoying.validation.email.EmailValidation;
import enjoying.validation.password.PasswordValidation;
import enjoying.validation.phoneNumber.PhoneNumberValidation;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record UpdateRequest(String fullName,
                            @EmailValidation @Email
                            String email,
                            @PasswordValidation
                            String password,
                            @DateOfBirthValidation
                            LocalDate dateOfBirth,
                            @PhoneNumberValidation
                            String phoneNumber,
                            String image
                            ) {
}
