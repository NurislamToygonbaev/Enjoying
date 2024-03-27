package enjoying.service.impl;

import enjoying.config.jwt.JwtService;
import enjoying.dto.request.UpdateRequest;
import enjoying.dto.response.*;
import enjoying.entities.Announcement;
import enjoying.entities.RentInfo;
import enjoying.entities.User;
import enjoying.exceptions.AlreadyExistsException;
import enjoying.exceptions.NotFoundException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.UserRepository;
import enjoying.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CurrentUser currentUser;
    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public FindAnnouncementAdminRes findById(Long anId) {
        Announcement announcement = announcementRepository.getAnnouncementByIdWhereIsActive(anId);
        return FindAnnouncementAdminRes.builder()
                .id(announcement.getId())
                .images(announcement.getImages())
                .title(announcement.getTitle())
                .houseType(announcement.getHouseType())
                .guest(announcement.getMaxGuests())
                .address(announcement.getAddress())
                .town(announcement.getTown())
                .description(announcement.getDescription())
                .image(announcement.getUser().getImage())
                .fullName(announcement.getUser().getFullName())
                .email(announcement.getUser().getEmail())
                .build();
    }

    @Override @Transactional
    public SimpleResponse announcementAccepted(Long anId) {
        Announcement announcement = announcementRepository.getAnnouncementById(anId);
        if (announcement.isActive()){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("already accepted")
                    .build();
        }
        announcement.setActive(true);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Accepted")
                .build();
    }

    @Override @Transactional
    public SimpleResponse announcementRejected(Long anId) {
        Announcement announcement = announcementRepository.getAnnouncementById(anId);
        User currenUser = currentUser.getCurrenUser();
        currenUser.setMoney(BigDecimal.valueOf(currenUser.getMoney().intValue() - 200));
        announcement.getUser().setMoney(BigDecimal.valueOf(announcement.getUser().getMoney().intValue() + 200));
        announcement.setReject("Unfortunately, we have to give up. Your money returned!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(announcement.getReject())
                .build();
    }

    @Override @Transactional
    public SimpleResponse announcementBlocked(Long userId) {
        User user = userRepo.getById(userId);
        List<Announcement> announcements = user.getAnnouncements();
        if (announcements.isEmpty()){
            throw new NotFoundException("no announcements");
        }
        for (Announcement announcement : announcements) {
            announcement.setBlock(true);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Blocked")
                .build();
    }

    @Override
    public List<AnnouncementBookingResponse> bookingAnnouncementByUser(Long userId) {
        User user = userRepo.getById(userId);
        List<AnnouncementBookingResponse> responses = new ArrayList<>();

        for (RentInfo rentInfo : user.getRentInfos()) {
            Announcement announcement = rentInfo.getAnnouncement();
            AnnouncementBookingResponse booking = new AnnouncementBookingResponse(
                    announcement.getImages(), announcement.getId(),
                    String.valueOf(announcement.getPrice()), announcement.getRating(),
                    announcement.getDescription(), announcement.getTown(),
                    announcement.getAddress(), announcement.getMaxGuests(),
                    rentInfo.getCheckIn(), rentInfo.getCheckOut()
            );
            responses.add(booking);
        }
        return responses;
    }

    @Override @Transactional
    public SimpleResponse blockAnnouncement(Long anId) {
        Announcement announcement = announcementRepository.getAnnouncementById(anId);
        if (announcement.isBlock()){
            announcement.setBlock(false);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("unBlocked")
                    .build();
        }else {
            announcement.setBlock(true);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Blocked")
                    .build();
        }
    }

    @Override
    public SimpleResponse deleteAnnouncement(Long anId) {
        Announcement announcement = announcementRepository.getAnnouncementByIdWhereIsActive(anId);
        announcementRepository.delete(announcement);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("deleted")
                .build();
    }

    @Override
    public SignResponse updateAdmin(UpdateRequest updateRequest) {
        User user = currentUser.getCurrenUser();
        boolean b = userRepo.existsByEmail(updateRequest.email());
        if(b) throw new AlreadyExistsException("This email: '" + updateRequest.email() + "' already exists!");
        user.setFullName(updateRequest.fullName());
        user.setEmail(updateRequest.email());
        user.setImage(updateRequest.image());
        user.setDateOfBirth(updateRequest.dateOfBirth());
        user.setPassword(passwordEncoder.encode(updateRequest.password()));
        user.setPhoneNumber(updateRequest.phoneNumber());
        userRepo.save(user);
        return SignResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .token(jwtService.createToken(user))
                .httpStatus(HttpStatus.OK)
                .message("Updated!")
                .build();
    }


}
