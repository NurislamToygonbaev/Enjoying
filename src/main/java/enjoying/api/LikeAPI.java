package enjoying.api;

import enjoying.dto.response.PopularResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.enums.Region;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeAPI {
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final FavoriteService favoriteService;
    private final FeedBackService feedBackService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;

    @PostMapping("/{feedId}")
    public SimpleResponse likeToFeedBack(@PathVariable Long feedId){
        return likeService.likeToFeedBack(feedId);
    }

    @PostMapping("/dis-like/{feedId}")
    public SimpleResponse disLikeToFeedBack(@PathVariable Long feedId){
        return likeService.disLikeToFeedBack(feedId);
    }

@GetMapping("/Popular7")
@Secured("CLIENT")
@Operation(description = "FindAll Popular 7 Announcement")
public List<PopularResponse> popularSeven(){
    return likeService.popularSeven();
}
@GetMapping("/regionAnnouncement")
@Operation(description = "Region's Announcement")
    public List<PopularResponse>regionAnnouncement(@RequestParam Region region){
        return likeService.regionAnnouncement(region);
}
}
