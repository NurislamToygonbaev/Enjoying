package enjoying.api;

import enjoying.dto.pagination.UserPagination;
import enjoying.dto.request.EditAnnouncementReq;
import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.*;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
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
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final FavoriteService favoriteService;
    private final FeedBackService feedBackService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;

    @Secured({"CLIENT", "VENDOR"})
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody @Valid SaveAnnouncementRequest saveAnnouncementRequest){
        return announcementService.save(saveAnnouncementRequest);
    }

    @Secured({"CLIENT", "VENDOR"})
    @GetMapping("get-all")
    public ResultPaginationAnnouncement paginationAnnouncements(PaginationRequest paginationRequest){
        return announcementService.getAll(paginationRequest);
    }


    @GetMapping("/booking")
    @Operation(description = "FindAll active Announcements where i was")
    public List<AnnouncementBookingResponse> bookingAcceptedAnnouncement(){
        return rentInfoService.bookingAcceptedAnnouncement();
    }

    @GetMapping("/my-announcements")
    @Operation(description = "my announcements")
    public List<MyAnnouncementResponses> myAnnouncements(){
        return likeService.myAnnouncements();
    }

    @GetMapping("/my-announcements-house-type")
    @Operation(description = "my announcements with house type")
    public List<MyAnnouncementResponses> myAnnouncementsWithHouseType(@RequestParam HouseType type){
        return likeService.myAnnouncementsWithHouseType(type);
    }

    @GetMapping("/my-announcements-high")
    @Operation(description = "my announcements high price")
    public List<MyAnnouncementResponses> myAnnouncementsHigh(){
        return likeService.myAnnouncementsHigh();
    }

    @GetMapping("/my-announcements-low")
    @Operation(description = "my announcements low price")
    public List<MyAnnouncementResponses> myAnnouncementsLow(){
        return likeService.myAnnouncementsLow();
    }

    @PutMapping("/{anId}")
    public SimpleResponse editMyAnnouncement(@PathVariable Long anId,
                                             @RequestBody EditAnnouncementReq req){
        return announcementService.editMyAnnouncement(anId, req);
    }

    @DeleteMapping("/{anId}")
    public SimpleResponse deleteMyAnnouncement(@PathVariable Long anId){
        return announcementService.deleteMyAnnouncement(anId);
    }

}
