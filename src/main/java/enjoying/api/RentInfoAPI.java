package enjoying.api;

import enjoying.dto.request.Bookingrequest;
import enjoying.dto.response.BookingResponse;
import enjoying.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rent_info")
public class RentInfoAPI {
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final FavoriteService favoriteService;
    private final FeedBackService feedBackService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;
    @PostMapping("/BookingAnnouncement/{announcementId}")
    @Secured("CLIENT")
    public BookingResponse bookingResPonse(@PathVariable Long announcementId,
                                          @RequestBody Bookingrequest bookingrequest){
        return rentInfoService.bookingAnnouncement(announcementId,bookingrequest);
    }



}
