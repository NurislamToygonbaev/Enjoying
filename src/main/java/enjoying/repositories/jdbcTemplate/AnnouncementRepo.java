package enjoying.repositories.jdbcTemplate;

import enjoying.dto.pagination.UserPagination;

public interface AnnouncementRepo {
    UserPagination findAllAcceptedAnnouncement(int page, int size);
}
