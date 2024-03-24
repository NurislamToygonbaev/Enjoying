package enjoying.service.impl;

import enjoying.config.jwt.JwtService;
import enjoying.dto.request.AddMoneyRequest;
import enjoying.dto.request.SignInRequest;
import enjoying.dto.request.SignUpRequest;
import enjoying.dto.request.UpdateRequest;
import enjoying.dto.response.*;
import enjoying.entities.Announcement;
import enjoying.entities.RentInfo;
import enjoying.entities.User;
import enjoying.enums.Role;
import enjoying.exceptions.AlreadyExistsException;
import enjoying.exceptions.ForbiddenException;
import enjoying.exceptions.NotFoundException;
import enjoying.repositories.AnnouncementRepository;
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
    private final AnnouncementRepository announcementRepository;

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
                .money(BigDecimal.ZERO)
                .image(signUpReques.getImage())
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
            if (!user.getRole().equals(Role.ADMIN)) responseList.add(response);

        }

        return responseList;
    }

    @Override
    public SimpleResponse deleteUser(Long userId) {
        User currenUser = currentUser.getCurrenUser();
        if (currenUser.getId().equals(userId)) {
            throw new ForbiddenException("The admin will not be able to delete himself");
        }
        if (currenUser.getRole().equals(Role.CLIENT) || currenUser.getRole().equals(Role.VENDOR)) {
            throw new ForbiddenException("Not acces");
        }

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

    @Override
    public SimpleResponse updateUser(UpdateRequest updateRequest) {
        User user = currentUser.getCurrenUser();
        checkEmail(updateRequest.email());
        user.setFullName(updateRequest.fullName());
        user.setEmail(updateRequest.email());
        user.setImage(updateRequest.image());
        user.setDateOfBirth(updateRequest.dateOfBirth());
        user.setPassword(updateRequest.password());
        user.setPhoneNumber(updateRequest.phoneNumber());
        userRepo.save(user);
        return SimpleResponse.builder().
                httpStatus(HttpStatus.OK).message("Updated!").build();
    }

    @Override
    public SimpleResponse addMoney(AddMoneyRequest addMoneyRequest) {
        User user = currentUser.getCurrenUser();
        if (user.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("Not acces");
        }
        user.setMoney(user.getMoney().add(BigDecimal.valueOf(addMoneyRequest.money())));
        userRepo.save(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Added money")
                        .
                build();
    }

    @Override
    public MyProfile myProfile() {
        User user = currentUser.getCurrenUser();
        return MyProfile.builder()
                .name(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public List<DataAnnouncement> dataAnnouncement() {
        User user = currentUser.getCurrenUser();
//        List<Announcement> announcements = new ArrayList<>();
//        for (Announcement announcement : announcements) {
//            DataAnnouncement.builder()
//                    .image(String.valueOf(announcement.getImages()))
//                    .price(announcement.getPrice())
//                    .rating(announcement.getRating())
//                    .title(announcement.getTitle())
//                    .region(announcement.getRegion())
//                    .town(announcement.getTown())
//                    .address(announcement.getAddress())
//                    .maxGuest(announcement.getMaxGuests())
//                    .checkin(announcement.getRentInfos())
//
//            build();
//        }
        return announcementRepository.dataAnnouncement();
    }
}
