package enjoying.api;

import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.MyAnnounceRequest;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.*;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
public class AnnouncementAPI {
    private final AnnouncementService announcementService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;

    @Secured({"CLIENT", "VENDOR"})
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid SaveAnnouncementRequest saveAnnouncementRequest) {
        return announcementService.save(saveAnnouncementRequest);
    }

    @Secured({"CLIENT", "VENDOR"})
    @GetMapping("get-all")
    public ResultPaginationAnnouncement paginationAnnouncements(PaginationRequest paginationRequest){
        return announcementService.getAll(paginationRequest);
    }

    @Secured({"CLIENT", "VENDOR", "ADMIN"})
    @GetMapping("/my-announcements")
    @Operation(description = "my announcements")
    public MyAnnouncementResponses myAnnouncements(MyAnnounceRequest myAnnounceRequest) {
        return announcementService.myAnnouncements(myAnnounceRequest);
    }

    @GetMapping("/booking")
    @Operation(description = "FindAll active Announcements where i was")
    public List<AnnouncementBookingResponse> bookingAcceptedAnnouncement() {
        return rentInfoService.bookingAcceptedAnnouncement();
    }

    @PutMapping("/{anId}")
    public SimpleResponse editMyAnnouncement(@PathVariable Long anId,
                                             @RequestBody EditAnnouncementReq req) {
        return announcementService.editMyAnnouncement(anId, req);
    }

    @DeleteMapping("/{anId}")
    public SimpleResponse deleteMyAnnouncement(@PathVariable Long anId) {
        return announcementService.deleteMyAnnouncement(anId);
    }

    @Secured({"CLIENT", "VENDOR", "ADMIN"})
    @GetMapping("/find-announcement/{anId}")
    @Operation(description = "find active Announcements")
    public FindAnnouncementByIdRes findByIdAnnouncement(@PathVariable Long anId) {
        return announcementService.findByIdAnnouncement(anId);
    }

























    @Secured({"CLIENT", "VENDOR", "ADMIN"})
    @GetMapping("/find-my-announcement/{anId}")
    @Operation(description = "find active Announcements")
    public FindMyAnnouncementByIdRes findMyAnnouncementById(@PathVariable Long anId) {
        return announcementService.findMyAnnouncementById(anId);
    }
}
