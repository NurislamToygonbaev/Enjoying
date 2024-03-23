package enjoying.api;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed_back")
public class FeedBackAPI {
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final FavoriteService favoriteService;
    private final FeedBackService feedBackService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;


    @PostMapping("/{anId}")
    public SimpleResponse saveFeedBack(@PathVariable Long anId,
                                       @RequestBody FeedBackSaveRequest request){
        return feedBackService.save(anId, request);
    }
}
