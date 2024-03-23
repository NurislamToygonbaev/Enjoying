package enjoying.api;

import enjoying.dto.request.PaginationRequest;
import enjoying.dto.request.announcement.SaveAnnouncementRequest;
import enjoying.dto.response.ResultPaginationAnnouncement;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
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
    public ResultPaginationAnnouncement paginationAnnouncements(@RequestBody PaginationRequest paginationRequest){
        return announcementService.getAll(paginationRequest);
    }

}
