package enjoying.service;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.SimpleResponse;

import java.util.List;

public interface FavoriteService {
    SimpleResponse likeAnnouncement(Long anId);

    List<AnnouncementResponses> myFavorites();
}
