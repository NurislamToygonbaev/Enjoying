package enjoying.api;

import enjoying.dto.request.UpdateRequest;
import enjoying.dto.response.*;
import enjoying.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAPI {
    private final AdminService adminService;
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

    @Secured("ADMIN")
    @PostMapping("/all-block/{userId}")
    public SimpleResponse announcementBlocked(@PathVariable Long userId){
        return adminService.announcementBlocked(userId);
    }


    @Secured("ADMIN")
    @GetMapping("/booking/{userId}")
    public List<AnnouncementBookingResponse> bookingAnnouncementByUser(@PathVariable Long userId){
        return adminService.bookingAnnouncementByUser(userId);
    }

    @Secured("ADMIN")
    @PostMapping("/block/{anId}")
    public SimpleResponse blockAnnouncement(@PathVariable Long anId){
        return adminService.blockAnnouncement(anId);
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{anId}")
    public SimpleResponse deleteAnnouncement(@PathVariable Long anId){
        return adminService.deleteAnnouncement(anId);
    }

    @PutMapping("/update")
    @Operation(description = "Update admin")
    @Secured({"ADMIN"})
    public SignResponse updateAdmin(@RequestBody UpdateRequest updateRequest) {
        return adminService.updateAdmin(updateRequest);
    }
}
