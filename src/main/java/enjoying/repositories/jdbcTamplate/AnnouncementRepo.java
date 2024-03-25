package enjoying.repositories.jdbcTamplate;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;

public interface AnnouncementRepo {
    UserPagination findAllAcceptedAnnouncement(int page, int size);
}