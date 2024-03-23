package enjoying.api;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.request.FeedBackUpdateReq;
import enjoying.dto.response.FindFeedBackResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import jakarta.validation.Valid;
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
                                       @RequestBody @Valid FeedBackSaveRequest request){
        return feedBackService.save(anId, request);
    }

    @DeleteMapping("/{feedId}")
    public SimpleResponse deleteFeedBack(@PathVariable Long feedId){
        return feedBackService.deleteFeedBack(feedId);
    }

    @PutMapping("/{feedId}")
    public SimpleResponse updateGeedBack(@PathVariable Long feedId,
                                         @RequestBody FeedBackUpdateReq req){
        return feedBackService.updateFeedBack(feedId, req);
    }

    @GetMapping("/{feedId}")
    public FindFeedBackResponse findFeedBack(@PathVariable Long feedId){
        return feedBackService.findFeedBack(feedId);
    }
}
