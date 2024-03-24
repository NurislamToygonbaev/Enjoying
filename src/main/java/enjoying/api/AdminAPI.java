package enjoying.api;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.response.FindAnnouncementAdminRes;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAPI {
    private final AdminService adminService;

    @Secured("ADMIN")
    @GetMapping
    @Operation(description = "FindAll no active Announcements")
    public UserPagination findAllAcceptedAnnouncement(@RequestParam int page,
                                                      @RequestParam int size){
        return adminService.findAllAcceptedAnnouncement(page, size);
    }

    @Secured("ADMIN")
    @GetMapping("/find/{anId}")
    public FindAnnouncementAdminRes findById(@PathVariable Long anId){
        return adminService.findById(anId);
    }

    @Secured("ADMIN")
    @PostMapping("/{anId}")
    public SimpleResponse announcementAccepted(@PathVariable Long anId){
        return adminService.announcementAccepted(anId);
    }

    @Secured("ADMIN")
    @PostMapping("/reject/{anId}")
    public SimpleResponse announcementRejected(@PathVariable Long anId){
        return adminService.announcementRejected(anId);
    }

}
