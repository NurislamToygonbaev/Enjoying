package enjoying.api;

import enjoying.dto.pagination.UserPagination;
import enjoying.service.AdminService;
import enjoying.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAPI {
    private final AdminService adminService;

    @Secured("ADMIN")
    @GetMapping
    public UserPagination findAllAcceptedAnnouncement(@RequestParam int page,
                                                      @RequestParam int size){
        return adminService.findAllAcceptedAnnouncement(page, size);
    }
}
