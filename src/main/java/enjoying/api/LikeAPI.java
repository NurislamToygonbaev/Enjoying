package enjoying.api;

import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
