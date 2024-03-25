package enjoying.service;

import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.FindAnnouncementByIdRes;
import enjoying.dto.response.ResultPaginationAnnouncement;
import enjoying.dto.response.SimpleResponse;

import java.util.List;

public interface AnnouncementService {
    SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest);

    ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest);

    SimpleResponse editMyAnnouncement(Long anId, EditAnnouncementReq req);

    SimpleResponse deleteMyAnnouncement(Long anId);

    FindAnnouncementByIdRes findByIdAnnouncement(Long anId);
}
