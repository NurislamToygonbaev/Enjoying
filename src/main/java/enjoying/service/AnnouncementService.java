package enjoying.service;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.FindAnnouncementByIdRes;
import enjoying.dto.response.ResultPaginationAnnouncement;
import enjoying.dto.response.SimpleResponse;
import enjoying.enums.HouseType;
import enjoying.enums.Region;

import java.util.List;

public interface AnnouncementService {
    SimpleResponse save(SaveAnnouncementRequest saveAnnouncementRequest);

    ResultPaginationAnnouncement getAll(PaginationRequest paginationRequest);

    UserPagination findAllAcceptedAnnouncement(int page, int size);

    UserPagination regionFilterAcceptedAnnouncement(int page, int size, Region region);

    UserPagination popularAcceptedAnnouncement(int page, int size);

    UserPagination houseTypeFilterAcceptedAnnouncement(int page, int size, HouseType houseType);

    UserPagination highPriceAcceptedAnnouncement(int page, int size);

    UserPagination lowPriceAcceptedAnnouncement(int page, int size);

    SimpleResponse editMyAnnouncement(Long anId, EditAnnouncementReq req);

    SimpleResponse deleteMyAnnouncement(Long anId);

    FindAnnouncementByIdRes findByIdAnnouncement(Long anId);
}
