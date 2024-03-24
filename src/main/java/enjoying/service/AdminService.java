package enjoying.service;

import enjoying.dto.pagination.UserPagination;

public interface AdminService {
    UserPagination findAllAcceptedAnnouncement(int page, int size);
}
