package enjoying.service.impl;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.dto.response.PopularResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.entities.Announcement;
import enjoying.entities.FeedBack;
import enjoying.entities.User;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.FeedBackRepository;
import enjoying.repositories.LikeRepository;
import enjoying.service.LikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepo;
    private final FeedBackRepository feedBackRepo;
    private final AnnouncementRepository announcementRepo;
    private final CurrentUser currentUser;

    @Override @Transactional
    public SimpleResponse likeToFeedBack(Long feedId) {
        Long userId = currentUser.getCurrenUser().getId();
        FeedBack feedBack = feedBackRepo.getFeedBackById(feedId);
        if (!feedBack.getLike().getLikes().contains(userId)){
            feedBack.getLike().getDisLikes().remove(userId);
            feedBack.getLike().getLikes().add(userId);
        }else {
            feedBack.getLike().getLikes().remove(userId);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Success")
                .build();
    }

    @Override @Transactional
    public SimpleResponse disLikeToFeedBack(Long feedId) {
        Long userId = currentUser.getCurrenUser().getId();
        FeedBack feedBack = feedBackRepo.getFeedBackById(feedId);
        if (!feedBack.getLike().getDisLikes().contains(userId)){
            feedBack.getLike().getLikes().remove(userId);
            feedBack.getLike().getDisLikes().add(userId);
        }else {
            feedBack.getLike().getDisLikes().remove(userId);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Success")
                .build();
    }

    @Override
    public List<MyAnnouncementResponses> myAnnouncements() {
        Long userId = currentUser.getCurrenUser().getId();
        return announcementRepo.myAnnouncements(userId);
    }


    @Override
    public List<PopularResponse> popularseven() {
       List<Announcement>popularResponseList = likeRepo.popularSeven();
       List<PopularResponse>newPopular = new ArrayList<>();
        for (Announcement announcement: popularResponseList) {
          PopularResponse popularResponse = PopularResponse.builder()
                  .photo(String.valueOf(announcement.getImages()))
                  .title(announcement.getTitle())
                  .description(announcement.getDescription())
                  .region(announcement.getRegion())
                  .town(announcement.getTown())
                  .address(announcement.getAddress())
            .build();
          newPopular.add(popularResponse);

        }
        return newPopular;
    }

    @Override
    public List<PopularResponse> regiomAnnouncement(Region region) {
        List<Announcement>announcements = likeRepo.regionAnnouncement(region);
        List<PopularResponse>regionAnnouncement = new ArrayList<>();
        for (Announcement announcement : announcements) {
            PopularResponse popularResponse = PopularResponse.builder()
                    .photo(String.valueOf(announcement.getImages()))
                    .title(announcement.getTitle())
                    .description(announcement.getDescription())
                    .region(announcement.getRegion())
                    .town(announcement.getTown())
                    .address(announcement.getAddress())
            .build();
            regionAnnouncement.add(popularResponse);

        }
        return regionAnnouncement;
    }
}
