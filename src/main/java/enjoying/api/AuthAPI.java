package enjoying.api;

import enjoying.dto.request.SignInRequest;
import enjoying.dto.response.SignResponse;
import enjoying.dto.request.SignUpRequest;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {
    private final UserService userService;
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
}

