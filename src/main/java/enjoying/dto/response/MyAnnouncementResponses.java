package enjoying.dto.response;

import enjoying.enums.HomePrice;
import enjoying.enums.HouseType;
import lombok.Builder;

import java.util.List;

@Builder
public record MyAnnouncementResponses(
        String userName,
        String contact,
        List<HouseType> houseTypes,
        HomePrice homePrice,
        int ratingFromFilter,
        List<MyAnnounceResponse>  myAnnounceResponses
){
}
