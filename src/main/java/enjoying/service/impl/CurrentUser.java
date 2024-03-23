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
                .email("admin@gmail.com")
                .dateOfBirth(LocalDate.of(2000, 12, 12))
                .image("AdminImage")
                .money(BigDecimal.valueOf(20000))
                .password(passwordEncoder.encode("admin"))
                .phoneNumber("+996707374093")
                .role(Role.ADMIN)
                .build());
        userRepo.save(User.builder()
                .fullName("mirlan Arstanbeekevvv")
                .email("arstanbeekovvv@gmail.com")
                .dateOfBirth(LocalDate.of(2002, 4, 12))
                .image("AdminImage")
                .money(BigDecimal.valueOf(20000))
                .password(passwordEncoder.encode("arstanbeekovvv"))
                .phoneNumber("+996771900091")
                .role(Role.CLIENT)
                .build());

    }

    public User getCurrenUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.getByEmail(email);
    }


}
