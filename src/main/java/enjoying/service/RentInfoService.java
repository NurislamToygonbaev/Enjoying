package enjoying.service;

import enjoying.dto.request.Bookingrequest;
import enjoying.dto.response.AnnouncementBookingResponse;
import enjoying.dto.response.BookingRes;
import enjoying.dto.response.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface RentInfoService {

    List<AnnouncementBookingResponse> bookingAcceptedAnnouncement();

    BookingRes bookingAnnouncement(Long announcementId, Bookingrequest bookingrequest);

}
