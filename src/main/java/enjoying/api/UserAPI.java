package enjoying.api;

import enjoying.dto.request.AddMoneyRequest;
import enjoying.dto.request.UpdateRequest;
import enjoying.dto.response.FindAllResponse;
import enjoying.dto.response.MyProfile;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAPI {
    private final UserService userService;

    @Secured("ADMIN")
    @PostMapping
    @Operation(description = "FindAll User")
    public List<FindAllResponse> findAlUser() {
        return userService.findAll();
    }

    @Secured("ADMIN")
    @DeleteMapping("/{userId}")
    public SimpleResponse deleteUserById(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping
    @Operation(description = "Update user")
    @Secured({"CLIENT", "VENDOR"})
    public SimpleResponse updateUser(@RequestBody UpdateRequest updateRequest) {
        return userService.updateUser(updateRequest);
    }
    @PostMapping("/addMoney")
    @Operation(description = "Add Money")
    @Secured({"CLIENT","VENDOR"})
    public SimpleResponse addMoney(@RequestBody AddMoneyRequest addMoneyRequest){
      return   userService.addMoney(addMoneyRequest);
    }

}
