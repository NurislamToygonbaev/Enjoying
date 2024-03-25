package enjoying.api;

import enjoying.dto.response.AnnouncementResponses;
import enjoying.dto.response.SimpleResponse;
import enjoying.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fav")
public class FavoriteAPI {
    private final FavoriteService favoriteService;

    @PostMapping("/{anId}")
    public SimpleResponse likeAnnouncement(@PathVariable Long anId){
        return favoriteService.likeAnnouncement(anId);
    }

    @GetMapping
    public List<AnnouncementResponses> myFavorites(){
        return favoriteService.myFavorites();
    }
}
