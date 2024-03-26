package enjoying.service;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.AnnouncementBookingResponse;
import enjoying.dto.response.FindAnnouncementAdminRes;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.dto.response.SimpleResponse;

import java.util.List;

public interface AdminService {
    FindAnnouncementAdminRes findById(Long anId);

    SimpleResponse announcementAccepted(Long anId);

    SimpleResponse announcementRejected(Long anId);

    SimpleResponse announcementBlocked(Long userId);

    List<MyAnnouncementResponses> userAnnouncements(Long userId);

    List<AnnouncementBookingResponse> bookingAnnouncementByUser(Long userId);

    SimpleResponse blockAnnouncement(Long anId);

    SimpleResponse deleteAnnouncement(Long anId);
}
