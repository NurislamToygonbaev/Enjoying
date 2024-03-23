package enjoying.api;

import enjoying.dto.request.SignInRequest;
import enjoying.dto.response.FindAllResponse;
import enjoying.dto.response.SignResponse;
import enjoying.dto.request.SignUpRequest;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {
    private final UserService userService;
    private final AnnouncementService announcementService;
    private final FavoriteService favoriteService;
    private final FeedBackService feedBackService;
    private final LikeService likeService;
    private final RentInfoService rentInfoService;
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

    @PostMapping("/findAllUser")
    @Operation(description = "FindAll User")
    public List<FindAllResponse> findAlUser(){
        return  userService.findAll();
    }}

