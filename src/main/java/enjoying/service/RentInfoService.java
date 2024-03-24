package enjoying.service;

import enjoying.dto.response.AnnouncementBookingResponse;

import java.util.List;

public interface RentInfoService {

    List<AnnouncementBookingResponse> bookingAcceptedAnnouncement();
}
