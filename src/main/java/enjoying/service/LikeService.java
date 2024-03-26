package enjoying.service;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.dto.response.PopularResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.enums.HouseType;
import enjoying.enums.Region;

import java.util.List;

public interface LikeService {
    SimpleResponse likeToFeedBack(Long feedId);

    SimpleResponse disLikeToFeedBack(Long feedId);

    List<MyAnnouncementResponses> myAnnouncements();

    List<PopularResponse> popularseven();

    List<PopularResponse> regiomAnnouncement(Region region);
}
