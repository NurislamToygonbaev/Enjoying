package enjoying.service;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.request.UpdateRequest;
import enjoying.dto.response.*;

import java.util.List;

public interface AdminService {
    FindAnnouncementAdminRes findById(Long anId);

    SimpleResponse announcementAccepted(Long anId);

    SimpleResponse announcementRejected(Long anId);

    SimpleResponse announcementBlocked(Long userId);

    List<AnnouncementBookingResponse> bookingAnnouncementByUser(Long userId);

    SimpleResponse blockAnnouncement(Long anId);

    SimpleResponse deleteAnnouncement(Long anId);

    SignResponse updateAdmin(UpdateRequest updateRequest);
}
