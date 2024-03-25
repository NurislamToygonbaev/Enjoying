package enjoying.service;

import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.MyAnnounceRequest;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.*;

public interface AnnouncementService {
    SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest);

    ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest);

    SimpleResponse editMyAnnouncement(Long anId, EditAnnouncementReq req);

    SimpleResponse deleteMyAnnouncement(Long anId);

    FindAnnouncementByIdRes findByIdAnnouncement(Long anId);

    MyAnnouncementResponses myAnnouncements(MyAnnounceRequest myAnnounceRequest);
}
