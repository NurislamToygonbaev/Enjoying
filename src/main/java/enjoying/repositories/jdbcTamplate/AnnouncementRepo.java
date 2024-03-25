package enjoying.repositories.jdbcTamplate;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;
import enjoying.enums.HouseType;
import enjoying.enums.Region;

public interface AnnouncementRepo {
    UserPagination findAllAcceptedAnnouncement(int page, int size);

    UserPagination findAllAnnouncement(int page, int size);

    UserPagination regionFilterAcceptedAnnouncement(int page, int size, Region region);

    UserPagination popularAcceptedAnnouncement(int page, int size);

    UserPagination houseTypeFilterAcceptedAnnouncement(int page, int size, HouseType houseType);

    UserPagination highPriceAcceptedAnnouncement(int page, int size);

    UserPagination lowPriceAcceptedAnnouncement(int page, int size);
}
