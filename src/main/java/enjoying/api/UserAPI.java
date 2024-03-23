package enjoying.api;

import enjoying.dto.response.FindAllResponse;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserAPI {
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final FavoriteService favoriteService;
    private final FeedBackService feedBackService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;

    @Secured("ADMIN")
    @PostMapping("/findAllUser")
    @Operation(description = "FindAll User")
    public List<FindAllResponse> findAlUser(){
        return  userService.findAll();
    }
    @Secured("ADMIN")
    @PostMapping("/deleteUser/{userId}")
    public SimpleResponse deleteUserById(@PathVariable Long userId){
        return  userService.deleteUser(userId);

}
}
