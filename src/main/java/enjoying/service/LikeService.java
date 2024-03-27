package enjoying.service;

import enjoying.dto.response.PopularResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.enums.Region;

import java.util.List;

public interface LikeService {
    SimpleResponse likeToFeedBack(Long feedId);

    SimpleResponse disLikeToFeedBack(Long feedId);


    List<PopularResponse> popularSeven();

    List<PopularResponse> regionAnnouncement(Region region);
}
