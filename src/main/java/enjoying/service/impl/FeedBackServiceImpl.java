package enjoying.service.impl;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.response.SimpleResponse;
import enjoying.entities.Announcement;
import enjoying.entities.FeedBack;
import enjoying.entities.RentInfo;
import enjoying.entities.User;
import enjoying.exceptions.ForbiddenException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.FeedBackRepository;
import enjoying.service.FeedBackService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {
    private final FeedBackRepository feedBackRepo;
    private final CurrentUser currentUser;
    private final AnnouncementRepository announcementRepo;

    @Override @Transactional
    public SimpleResponse save(Long anId, FeedBackSaveRequest request) {
        User user = currentUser.getCurrenUser();
        Announcement announcement = announcementRepo.getAnnouncementById(anId);
        List<RentInfo> rentInfos = announcement.getRentInfos();
        List<RentInfo> userRentInfos = user.getRentInfos();

        if (userRentInfos.isEmpty()) {
            throw new ForbiddenException("You cannot leave feedback without any bookings.");
        }

        for (RentInfo userRentInfo : userRentInfos) {
            if (!rentInfos.contains(userRentInfo)){
                throw new ForbiddenException("feedback can be left only after booking");
            }
        }
        FeedBack feedBack = feedBackRepo.save(FeedBack.builder()
                .images(request.images())
                .rating(request.rating())
                .description(request.description())
                .build());
        feedBack.setAnnouncement(announcement);
        announcement.getFeedBacks().add(feedBack);
        user.getFeedBacks().add(feedBack);
        feedBack.setUser(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("you have successfully written a review")
                .build();
    }
}
