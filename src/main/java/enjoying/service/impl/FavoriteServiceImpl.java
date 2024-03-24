package enjoying.service.impl;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.SimpleResponse;
import enjoying.entities.Announcement;
import enjoying.entities.User;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.FavoriteRepository;
import enjoying.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final CurrentUser currentUser;
    private final AnnouncementRepository announcementRepo;

    @Override
    public SimpleResponse likeAnnouncement(Long anId) {
        User user = currentUser.getCurrenUser();
        Announcement announcement = announcementRepo.getAnnouncementById(anId);
        if (!user.getFavorite().getAnnouncements().contains(announcement)){
            user.getFavorite().getAnnouncements().add(announcement);
        }else {
            user.getFavorite().getAnnouncements().remove(announcement);
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("success")
                .build();
    }

    @Override
    public List<AnnouncementResponses> myFavorites() {
        User user = currentUser.getCurrenUser();
        List<Announcement> announcements = user.getFavorite().getAnnouncements();
        List<AnnouncementResponses> announcementResponses = new ArrayList<>();
        for (Announcement announcement : announcements) {
            AnnouncementResponses responses = new AnnouncementResponses(
                    announcement.getImages(), announcement.getId(), String.valueOf(announcement.getPrice()),
                    announcement.getRating(), announcement.getDescription(), announcement.getTown(),
                    announcement.getAddress(), announcement.getMaxGuests()
            );
            announcementResponses.add(responses);
        }
        return announcementResponses;
    }
}
