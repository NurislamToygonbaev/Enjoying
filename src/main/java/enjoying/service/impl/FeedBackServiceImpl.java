package enjoying.service.impl;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.request.FeedBackUpdateReq;
import enjoying.dto.response.FindFeedBackResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.entities.*;
import enjoying.exceptions.ForbiddenException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.FeedBackRepository;
import enjoying.repositories.LikeRepository;
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
    private final LikeRepository likeRepository;

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
        Like like = new Like();
        likeRepository.save(like);
        like.setFeedBack(feedBack);
        feedBack.setLike(like);

        double rating = announcementRating(announcement.getFeedBacks());
        double roundedRating = Math.round(rating * 10.0) / 10.0;
        double limitedRating = Math.min(roundedRating, 5.0);
        announcement.setRating(limitedRating);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("you have successfully written a review")
                .build();
    }
    private double announcementRating(List<FeedBack> feedbacks) {
        if (feedbacks.isEmpty()) {
            return 0;
        }

        double sumRatings = 0;
        for (FeedBack feedback : feedbacks) {
            sumRatings += feedback.getRating();
        }

        double averageRating = sumRatings / feedbacks.size();

        return averageRating * (5.0 / getMaxRating(feedbacks));
    }
    private double getMaxRating(List<FeedBack> feedbacks) {
        double maxRating = Double.MIN_VALUE;
        for (FeedBack feedback : feedbacks) {
            if (feedback.getRating() > maxRating) {
                maxRating = feedback.getRating();
            }
        }
        return maxRating;
    }

    @Override
    public SimpleResponse deleteFeedBack(Long feedId) {
        FeedBack feedBack = feedBackRepo.getFeedBackById(feedId);
        User user = currentUser.getCurrenUser();
        if (!feedBack.getUser().equals(user)){
            throw new ForbiddenException("you can't delete other people's reviews");
        }
        feedBackRepo.delete(feedBack);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("you have successfully deleted")
                .build();
    }

    @Override
    public SimpleResponse updateFeedBack(Long feedId, FeedBackUpdateReq req) {
        User user = currentUser.getCurrenUser();
        FeedBack feedBack = feedBackRepo.getFeedBackById(feedId);
        if (!user.equals(feedBack.getUser())){
            throw new ForbiddenException("you can't update other people's reviews");
        }
        feedBack.setImages(req.images());
        feedBack.setDescription(req.description());
        feedBackRepo.save(feedBack);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("you have successfully updated")
                .build();
    }

    @Override
    public FindFeedBackResponse findFeedBack(Long feedId) {
        FeedBack feedBack = feedBackRepo.getFeedBackById(feedId);
        return FindFeedBackResponse.builder()
                .rating(feedBack.getRating())
                .images(feedBack.getImages())
                .description(feedBack.getDescription())
                .likes(feedBack.getLike().getLikes().size())
                .likes(feedBack.getLike().getDisLikes().size())
                .build();
    }
}
