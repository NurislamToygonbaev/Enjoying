package enjoying.service.impl;


import enjoying.dto.response.AnnouncementBookingResponse;
import enjoying.entities.Announcement;
import enjoying.entities.RentInfo;
import enjoying.entities.User;
import enjoying.repositories.RentInfoRepository;
import enjoying.service.RentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentInfoServiceImpl implements RentInfoService {
    private final RentInfoRepository rentInfoRepo;
    private final CurrentUser currentUser;

    @Override
    public List<AnnouncementBookingResponse> bookingAcceptedAnnouncement() {
        User user = currentUser.getCurrenUser();
        List<RentInfo> rentInfos = user.getRentInfos();
        List<AnnouncementBookingResponse> responses = new ArrayList<>();

        for (RentInfo rentInfo : rentInfos) {
            Announcement announcement = rentInfo.getAnnouncement();
            AnnouncementBookingResponse booking = new AnnouncementBookingResponse(
                    announcement.getImages(), announcement.getId(),
                    String.valueOf(announcement.getPrice()), announcement.getRating(),
                    announcement.getDescription(), announcement.getTown(),
                    announcement.getAddress(), announcement.getMaxGuests(),
                    rentInfo.getCheckIn(), rentInfo.getCheckOut()
                    );
            responses.add(booking);
        }
        return responses;
    }
}
