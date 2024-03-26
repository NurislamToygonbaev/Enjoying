package enjoying.dto.response;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import lombok.Builder;

import java.util.List;

@Builder
public record FindMyAnnouncementByIdRes(
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
        String price,
        List<BookingResponse> bookingResponses,
        List<FavoritesUserResponse> userResponses,
        List<AllFeedBackResponse> feedBackResponses,
        double rating,
        int five,
        int four,
        int three,
        int two,
        int one

) {
}
