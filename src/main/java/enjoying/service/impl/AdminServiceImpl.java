package enjoying.service.impl;

import enjoying.dto.pagination.UserPagination;
import enjoying.entities.Announcement;
import enjoying.entities.User;
import enjoying.enums.Role;
import enjoying.exceptions.ForbiddenException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.UserRepository;
import enjoying.repositories.jdbcTemplate.AnnouncementRepo;
import enjoying.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepo;
    private final CurrentUser currentUser;
    private final AnnouncementRepo announcementRepo;

    @Override
    public UserPagination findAllAcceptedAnnouncement(int page, int size) {
        User user = currentUser.getCurrenUser();
        if (!user.getRole().equals(Role.ADMIN)){
            throw new ForbiddenException("only the admin can see");
        }
        return announcementRepo.findAllAcceptedAnnouncement(page, size);
    }
}
