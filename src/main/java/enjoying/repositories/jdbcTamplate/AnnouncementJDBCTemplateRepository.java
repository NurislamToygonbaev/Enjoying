package enjoying.repositories.jdbcTamplate;

import enjoying.dto.request.MyAnnounceRequest;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.response.MyAnnouncementResponses;
import enjoying.dto.response.ResultPaginationAnnouncement;

public interface AnnouncementJDBCTemplateRepository {


    ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest);

    MyAnnouncementResponses myAnnouncements(Long userId, MyAnnounceRequest myAnnounceRequest);
}
