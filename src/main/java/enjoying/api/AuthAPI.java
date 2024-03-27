package enjoying.api;

import enjoying.dto.pagination.ResultSearchAnnouncement;
import enjoying.dto.request.SearchRequest;
import enjoying.dto.request.SignInRequest;
import enjoying.dto.response.PopularResponse;
import enjoying.dto.response.ResultPaginationAnnouncement;
import enjoying.dto.response.SignResponse;
import enjoying.dto.request.SignUpRequest;
import enjoying.enums.Region;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {
    private final UserService userService;
    private final LikeService likeService;
    private final AnnouncementService announcementService;
    @PostMapping("/SignUp")
    @Operation(description = "SignUpUser")
    public SignResponse simpleResponse(@RequestBody @Valid SignUpRequest signUpReques){
        return userService.signUpUser(signUpReques);
    }
    @PostMapping("/SigIn")
    @Operation(description = "SignIn")
    public SignResponse signIn(@RequestBody @Valid SignInRequest signInRequest){
        return userService.signIn(signInRequest);
    }
    @GetMapping("/Popular7")
    @Operation(description = "FindAll Popular 7 Announcement")
    public List<PopularResponse> popularSeven() {
        return likeService.popularSeven();
    }

    @GetMapping("/regionAnnouncement")
    @Operation(description = "Region's Announcement")
    public List<PopularResponse> regionAnnouncement(@RequestParam Region region) {
        return likeService.regionAnnouncement(region);
    }

    @GetMapping("/search")
    @Operation(description = "search")
    public ResultSearchAnnouncement searchAnnouncements(SearchRequest searchRequest){
        return announcementService.searchAnnouncements(searchRequest);
    }


}

