package enjoying.service.impl;

import enjoying.config.jwt.JwtService;
import enjoying.dto.request.SignInRequest;
import enjoying.dto.request.SignUpRequest;
import enjoying.dto.response.FindAllResponse;
import enjoying.dto.response.SignResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.entities.User;
import enjoying.enums.Role;
import enjoying.exceptions.AlreadyExistsException;
import enjoying.exceptions.NotFoundException;
import enjoying.repositories.UserRepository;
import enjoying.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CurrentUser currentUser;

    private void checkEmail(String email) {
        boolean exists = userRepo.existsByEmail(email);
        if (exists) throw new AlreadyExistsException("User with email: " + email + " already have");
    }

    @Transactional
    @Override
    public SignResponse signUpUser(SignUpRequest signUpReques) {
        checkEmail(signUpReques.getEmail());
        final var user = userRepo.save(User.builder()
                .fullName(signUpReques.getFullName())
                .email(signUpReques.getEmail())
                .dateOfBirth(signUpReques.getDateOfBirth())
                .image(signUpReques.getImage())
                .money(BigDecimal.valueOf(signUpReques.getMoney()))
                .phoneNumber(signUpReques.getPhoneNumber())
                .password(passwordEncoder.encode(signUpReques.getPhoneNumber()))
                .role(Role.CLIENT)
                .build());

        return SignResponse.builder()
                .token(jwtService.createToken(user))
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .httpStatus(HttpStatus.OK)
                .message("Successful login")
                .build();
    }

    @Override
    public SignResponse signIn(SignInRequest signInRequest) {
        User user = userRepo.getByEmail(signInRequest.email());
        boolean matches = passwordEncoder.matches(signInRequest.password(), user.getPassword());
        if (!matches) throw new NotFoundException("Invalid Password");
        return SignResponse.builder()
                .token(jwtService.createToken(user))
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .httpStatus(HttpStatus.OK)
                .message("Successful login")
                .build();
    }

    @Override
    public List<FindAllResponse> findAll() {
        List<User> userList = userRepo.findAll();
        List<FindAllResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            FindAllResponse response = FindAllResponse.builder()
                    .fulName(user.getFullName())
                    .email(user.getEmail())
                    .announcement(user.getAnnouncements().size())
                    .booking(user.getRentInfos().size())
                    .build();

            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        currentUser.getCurrenUser();
        Optional<User> optionalUser = userRepo.findById(userId);

        if (optionalUser.isPresent()) {
            userRepo.deleteById(userId);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Deleted")

                    .build();
        } else {
            throw new NotFoundException("User with id: " + userId + "notFound");
        }
    }
}
