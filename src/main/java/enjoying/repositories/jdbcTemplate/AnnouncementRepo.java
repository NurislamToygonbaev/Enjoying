package enjoying.repositories.jdbcTemplate;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.DataAnnouncement;
import enjoying.dto.response.FindAnnouncementAdminRes;

import java.util.List;

public interface AnnouncementRepo {
    UserPagination findAllAcceptedAnnouncement(int page, int size);


}
