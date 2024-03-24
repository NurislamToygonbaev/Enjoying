package enjoying.repositories.jdbcTemplate;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;

public interface AnnouncementRepo {
    UserPagination findAllAcceptedAnnouncement(int page, int size);
}
