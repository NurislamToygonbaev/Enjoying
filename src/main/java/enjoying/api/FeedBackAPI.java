package enjoying.api;

import enjoying.dto.request.FeedBackSaveRequest;
import enjoying.dto.request.FeedBackUpdateReq;
import enjoying.dto.response.FindFeedBackResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed_back")
public class FeedBackAPI {
    private final FeedBackService feedBackService;


    @Secured({"ADMIN", "VENDOR", "CLIENT"})
    @PostMapping("/{anId}")
    public SimpleResponse saveFeedBack(@PathVariable Long anId,
                                       @RequestBody @Valid FeedBackSaveRequest request){
        return feedBackService.save(anId, request);
    }

    @Secured({"ADMIN", "VENDOR", "CLIENT"})
    @DeleteMapping("/{feedId}")
    public SimpleResponse deleteFeedBack(@PathVariable Long feedId){
        return feedBackService.deleteFeedBack(feedId);
    }

    @Secured({"ADMIN", "VENDOR", "CLIENT"})
    @PutMapping("/{feedId}")
    public SimpleResponse updateGeedBack(@PathVariable Long feedId,
                                         @RequestBody FeedBackUpdateReq req){
        return feedBackService.updateFeedBack(feedId, req);
    }

    @Secured({"ADMIN", "VENDOR", "CLIENT"})
    @GetMapping("/{feedId}")
    public FindFeedBackResponse findFeedBack(@PathVariable Long feedId){
        return feedBackService.findFeedBack(feedId);
    }
}
