package enjoying.dto.response;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import lombok.Builder;

import java.util.List;

@Builder
public record FindAnnouncementByIdRes(
        List<String> images,
        HouseType houseType,
        int guest,
        String title,
        String address,
        String town,
        Region region,
        String description,
        String image,
        String fullName,
        String email,
        List<AllFeedBackResponse> feedBackResponses,
        double rating,
        int five,
        int four,
        int three,
        int two,
        int one

) {
}
