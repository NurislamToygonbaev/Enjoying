package enjoying.service;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.dto.response.SimpleResponse;
import enjoying.enums.HouseType;

import java.util.List;

public interface LikeService {
    SimpleResponse likeToFeedBack(Long feedId);

    SimpleResponse disLikeToFeedBack(Long feedId);

    List<MyAnnouncementResponses> myAnnouncements();

    List<MyAnnouncementResponses> myAnnouncementsWithHouseType(HouseType type);

    List<MyAnnouncementResponses> myAnnouncementsHigh();

    List<MyAnnouncementResponses> myAnnouncementsLow();
}
