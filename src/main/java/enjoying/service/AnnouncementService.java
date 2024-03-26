package enjoying.service;

import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.*;

import java.util.List;

public interface AnnouncementService {
    SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest);

    ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest);

    SimpleResponse editMyAnnouncement(Long anId, EditAnnouncementReq req);

    SimpleResponse deleteMyAnnouncement(Long anId);

    FindAnnouncementByIdRes findByIdAnnouncement(Long anId);

    FindMyAnnouncementByIdRes findMyAnnouncementById(Long anId);
}
