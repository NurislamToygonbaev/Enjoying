package enjoying.service.impl;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;
import enjoying.entities.Announcement;
import enjoying.entities.User;
import enjoying.enums.Role;
import enjoying.exceptions.ForbiddenException;
import enjoying.repositories.AnnouncementRepository;
import enjoying.repositories.jdbcTamplate.AnnouncementRepo;
import enjoying.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CurrentUser currentUser;
    private final AnnouncementRepo announcementRepo;
    private final AnnouncementRepository announcementRepository;

    @Override
    public UserPagination findAllAcceptedAnnouncement(int page, int size) {
        User user = currentUser.getCurrenUser();
        if (!user.getRole().equals(Role.ADMIN)){
            throw new ForbiddenException("only the admin can see");
        }
        return announcementRepo.findAllAcceptedAnnouncement(page, size);
    }

    @Override
    public FindAnnouncementAdminRes findById(Long anId) {
        Announcement announcement = announcementRepository.getAnnouncementByIdWhereIsActive(anId);
        return FindAnnouncementAdminRes.builder()
                .id(announcement.getId())
                .images(announcement.getImages())
                .title(announcement.getTitle())
                .houseType(announcement.getHouseType())
                .guest(announcement.getMaxGuests())
                .address(announcement.getAddress())
                .town(announcement.getTown())
                .description(announcement.getDescription())
                .image(announcement.getUser().getImage())
                .fullName(announcement.getUser().getFullName())
                .email(announcement.getUser().getEmail())
                .build();
    }
}
