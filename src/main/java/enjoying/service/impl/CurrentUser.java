package enjoying.service.impl;

import enjoying.entities.User;
import enjoying.enums.Role;
import enjoying.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CurrentUser {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void saveAdmin() {
        userRepo.save(User.builder()
                .fullName("Admin")
                .email("Admin@gmail.com")
                .dateOfBirth(LocalDate.of(2000, 12, 12))
                .image("AdminImage")
                .money(BigDecimal.valueOf(20000))
                .password(passwordEncoder.encode("admin"))
                .phoneNumber("+996707374093")
                .role(Role.ADMIN)
                .build());
    }

    public User getCurrenUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.getByEmail(email);
    }


}
