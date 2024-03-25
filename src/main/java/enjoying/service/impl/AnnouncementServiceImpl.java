package enjoying.service.impl;

import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.MyAnnounceRequest;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.*;
import enjoying.entities.*;
import enjoying.enums.Role;
import enjoying.exceptions.BedRequestException;
import enjoying.exceptions.ForbiddenException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.UserRepository;
import enjoying.repositories.jdbcTamplate.AnnouncementJDBCTemplateRepository;
import enjoying.service.AnnouncementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepo;
    private final AnnouncementJDBCTemplateRepository templateRepository;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest) {
        User user = currentUser.getCurrenUser();
        if (user.getMoney().intValue() < 200) {
            throw new BedRequestException("You don't have enough money. To publish a post, you need to pay 200 som");
        }
        user.setMoney(BigDecimal.valueOf(user.getMoney().intValue() - 200));
        User admin = userRepository.findByRole(Role.ADMIN);
        admin.setMoney(BigDecimal.valueOf(admin.getMoney().intValue() + 200));
        Announcement announcement = new Announcement();
        announcement.setBlock(false);
        announcement.setImages(saveAnnouncementRequest.images());
        announcement.setTitle(saveAnnouncementRequest.title());
        announcement.setHouseType(saveAnnouncementRequest.houseType());
        announcement.setPrice(BigDecimal.valueOf(saveAnnouncementRequest.price()));
        announcement.setMaxGuests(saveAnnouncementRequest.maxOfQuests());
        announcement.setDescription(saveAnnouncementRequest.descriptionOfListing());
        announcement.setRegion(saveAnnouncementRequest.region());
        announcement.setTown(saveAnnouncementRequest.town());
        announcement.setAddress(saveAnnouncementRequest.address());
        user.setRole(Role.VENDOR);
        announcementRepo.save(announcement);
        user.getAnnouncements().add(announcement);
        announcement.setUser(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully to publish a post!")
                .build();
    }

    @Override
    public ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest) {
        return templateRepository.getAll(paginationRequest);
    }


    @Override
    @Transactional
    public SimpleResponse editMyAnnouncement(Long anId, EditAnnouncementReq req) {
        User user = currentUser.getCurrenUser();
        Announcement announcement = announcementRepo.getAnnouncementById(anId);
        if (!user.equals(announcement.getUser())) {
            throw new ForbiddenException("no access");
        }
        announcement.setImages(req.images());
        announcement.setHouseType(req.houseType());
        announcement.setMaxGuests(req.maxOfQuests());
        announcement.setPrice(BigDecimal.valueOf(req.price()));
        announcement.setTitle(req.title());
        announcement.setDescription(req.description());
        announcement.setRegion(req.region());
        announcement.setTown(req.town());
        announcement.setAddress(req.address());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("successfully edited")
                .build();
    }

    @Override
    public SimpleResponse deleteMyAnnouncement(Long anId) {
        User user = currentUser.getCurrenUser();
        Announcement announcement = announcementRepo.getAnnouncementById(anId);
        if (!user.equals(announcement.getUser())) {
            throw new ForbiddenException("no access");
        }
        announcementRepo.delete(announcement);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("successfully deleted")
                .build();
    }

    @Override
    public FindAnnouncementByIdRes findByIdAnnouncement(Long anId) {
        Announcement announcement = announcementRepo.getAnnouncementByIdWhereIsActiveTrue(anId);
        List<FeedBack> feedBacks = announcement.getFeedBacks();

        List<AllFeedBackResponse> allFeedBackResponses = feedBacks.stream()
                .map(this::convertToFeedBack)
                .collect(Collectors.toList());

        int five = 0, four = 0, three = 0, two = 0, one = 0;
        for (FeedBack feedBack : feedBacks) {
            if (feedBack.getRating() == 5) {
                five++;
            }
            if (feedBack.getRating() == 4) {
                four++;
            }
            if (feedBack.getRating() == 3) {
                three++;
            }
            if (feedBack.getRating() == 2) {
                two++;
            }
            if (feedBack.getRating() == 1) {
                one++;
            }
        }

        return FindAnnouncementByIdRes.builder()
                .images(announcement.getImages())
                .houseType(announcement.getHouseType())
                .guest(announcement.getMaxGuests())
                .title(announcement.getTitle())
                .address(announcement.getAddress())
                .town(announcement.getTown())
                .region(announcement.getRegion())
                .description(announcement.getDescription())
                .image(announcement.getUser().getImage())
                .fullName(announcement.getUser().getFullName())
                .email(announcement.getUser().getEmail())
                .feedBackResponses(allFeedBackResponses)
                .five(five!=0? five * 100 / feedBacks.size() : 0)
                .four(four!=0? four * 100 / feedBacks.size() : 0)
                .three(three!=0? three * 100 / feedBacks.size() : 0)
                .two(two!=0? two * 100 / feedBacks.size() : 0)
                .one(one!=0? one * 100 / feedBacks.size() : 0)
                .build();
    }

    @Override
    public MyAnnouncementResponses myAnnouncements(MyAnnounceRequest myAnnounceRequest) {
        return templateRepository.myAnnouncements(currentUser.getCurrenUser().getId(), myAnnounceRequest);
    }
    public FindMyAnnouncementByIdRes findMyAnnouncementById (Long anId){
            Announcement announcement = announcementRepo.getAnnouncementByIdWhereIsActive(anId);
            List<FeedBack> feedBacks = announcement.getFeedBacks();
            User user = currentUser.getCurrenUser();
            if (!user.equals(announcement.getUser())) {
                throw new ForbiddenException("you can't see this information");
            }

            List<BookingResponse> bookingResponses = new ArrayList<>();
            for (RentInfo rentInfo : announcement.getRentInfos()) {
                BookingResponse bookingResponse = new BookingResponse(
                        rentInfo.getCheckIn(), rentInfo.getCheckOut(),
                        rentInfo.getUser().getImage(), rentInfo.getUser().getFullName(),
                        rentInfo.getUser().getEmail()
                );
                bookingResponses.add(bookingResponse);
            }

            List<AllFeedBackResponse> allFeedBackResponses = feedBacks.stream()
                    .map(this::convertToFeedBack)
                    .collect(Collectors.toList());

            List<FavoritesUserResponse> userResponses = new ArrayList<>();
            for (Favorite favorite : announcement.getFavorites()) {
                FavoritesUserResponse userResponse = new FavoritesUserResponse(
                        favorite.getUser().getImage(), favorite.getUser().getFullName(),
                        favorite.getUser().getEmail(), favorite.getCreatedAt()
                );
                userResponses.add(userResponse);
            }

            int five = 0, four = 0, three = 0, two = 0, one = 0;
            for (FeedBack feedBack : feedBacks) {
                if (feedBack.getRating() == 5) {
                    five++;
                }
                if (feedBack.getRating() == 4) {
                    four++;
                }
                if (feedBack.getRating() == 3) {
                    three++;
                }
                if (feedBack.getRating() == 2) {
                    two++;
                }
                if (feedBack.getRating() == 1) {
                    one++;
                }
            }

            return FindMyAnnouncementByIdRes.builder()
                    .images(announcement.getImages())
                    .houseType(announcement.getHouseType())
                    .guest(announcement.getMaxGuests())
                    .title(announcement.getTitle())
                    .address(announcement.getAddress())
                    .town(announcement.getTown())
                    .region(announcement.getRegion())
                    .price(String.valueOf("$ " + announcement.getPrice() + " / day"))
                    .description(announcement.getDescription())
                    .image(announcement.getUser().getImage())
                    .fullName(announcement.getUser().getFullName())
                    .email(announcement.getUser().getEmail())
                    .userResponses(userResponses)
                    .bookingResponses(bookingResponses)
                    .feedBackResponses(allFeedBackResponses)
                    .five(five != 0 ? five * 100 / feedBacks.size() : 0)
                    .four(four != 0 ? four * 100 / feedBacks.size() : 0)
                    .three(three != 0 ? three * 100 / feedBacks.size() : 0)
                    .two(two != 0 ? two * 100 / feedBacks.size() : 0)
                    .one(one != 0 ? one * 100 / feedBacks.size() : 0)
                    .build();

    }
    private AllFeedBackResponse convertToFeedBack(FeedBack feedBack) {
        return new AllFeedBackResponse(
                feedBack.getUser().getImage(), feedBack.getUser().getFullName(),
                feedBack.getCreatedAt(), feedBack.getRating(),
                feedBack.getDescription(), feedBack.getLike().getLikes().size(),
                feedBack.getLike().getDisLikes().size()
        );
    }
}
