package enjoying.service.impl;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.*;
import enjoying.entities.Announcement;
import enjoying.entities.FeedBack;
import enjoying.entities.User;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.enums.Role;
import enjoying.exceptions.BedRequestException;
import enjoying.exceptions.ForbiddenException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.UserRepository;
import enjoying.repositories.jdbcTemplate.AnnouncementRepo;
import enjoying.service.AnnouncementService;
import enjoying.validation.experience.ExperienceValidation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepo;
    private final AnnouncementRepo repo;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest) {
        User user = currentUser.getCurrenUser();
        if(user.getMoney().intValue() < 200) {
            throw  new BedRequestException("You don't have enough money. To publish a post, you need to pay 200 som");
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
        Pageable pageable = PageRequest.of(paginationRequest.page()-1 , paginationRequest.size());
        Page<Announcement> announcementPage = announcementRepo.findAll(pageable);

        List<ForPagination> resultPagination = new ArrayList<>();

        return ResultPaginationAnnouncement.builder()
                .page(announcementPage.getNumber())
                .size(announcementPage.getTotalPages())
                .paginations(resultPagination)
                .build();
    }

    @Override
    public UserPagination findAllAcceptedAnnouncement(int page, int size) {
        return repo.findAllAnnouncement(page, size);
    }

    @Override
    public UserPagination regionFilterAcceptedAnnouncement(int page, int size, Region region) {
        return repo.regionFilterAcceptedAnnouncement(page, size, region);
    }

    @Override
    public UserPagination popularAcceptedAnnouncement(int page, int size) {
        return repo.popularAcceptedAnnouncement(page, size);
    }

    @Override
    public UserPagination houseTypeFilterAcceptedAnnouncement(int page, int size, HouseType houseType) {
        return repo.houseTypeFilterAcceptedAnnouncement(page, size, houseType);
    }

    @Override
    public UserPagination highPriceAcceptedAnnouncement(int page, int size) {
        return repo.highPriceAcceptedAnnouncement(page, size);
    }

    @Override
    public UserPagination lowPriceAcceptedAnnouncement(int page, int size) {
        return repo.lowPriceAcceptedAnnouncement(page, size);
    }

    @Override @Transactional
    public SimpleResponse editMyAnnouncement(Long anId, EditAnnouncementReq req) {
        User user = currentUser.getCurrenUser();
        Announcement announcement = announcementRepo.getAnnouncementById(anId);
        if (!user.equals(announcement.getUser())){
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
        if (!user.equals(announcement.getUser())){
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
        List<AllFeedBackResponse> responseList = new ArrayList<>();
        Announcement announcement = announcementRepo.getAnnouncementByIdWhereIsActiveTrue(anId);
        List<FeedBack> feedBacks = announcement.getFeedBacks();
        for (FeedBack feedBack : feedBacks) {
            AllFeedBackResponse backResponse = new AllFeedBackResponse(
                    feedBack.getUser().getImage(), feedBack.getUser().getFullName(),
                    feedBack.getCreatedAt(), feedBack.getRating(),
                    feedBack.getDescription(), feedBack.getLike().getLikes().size(),
                    feedBack.getLike().getDisLikes().size()
            );
            responseList.add(backResponse);
        }
        int five = 0, four = 0, three = 0, two = 0, one = 0;
        for (FeedBack feedBack : feedBacks) {
            if (feedBack.getRating() == 5){
                five++;
            }
            if (feedBack.getRating() == 4){
                four++;
            }
            if (feedBack.getRating() == 3){
                three++;
            }
            if (feedBack.getRating() == 2){
                two++;
            }
            if (feedBack.getRating() == 1){
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
                .feedBackResponses(responseList)
                .five(five*100 / feedBacks.size())
                .four(four*100 / feedBacks.size())
                .three(three*100 / feedBacks.size())
                .two(two*100 / feedBacks.size())
                .one(one*100 / feedBacks.size())
                .build();
    }
}
