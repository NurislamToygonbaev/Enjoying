package enjoying.service;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;
import enjoying.dto.response.SimpleResponse;

public interface AdminService {
    UserPagination findAllAcceptedAnnouncement(int page, int size);

    FindAnnouncementAdminRes findById(Long anId);

    SimpleResponse announcementAccepted(Long anId);

    SimpleResponse announcementRejected(Long anId);
}
