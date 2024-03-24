package enjoying.service;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;

public interface AdminService {
    UserPagination findAllAcceptedAnnouncement(int page, int size);

    FindAnnouncementAdminRes findById(Long anId);
}
