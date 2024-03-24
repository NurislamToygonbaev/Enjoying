package enjoying.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AnnouncementResponses(
        Long id,
        List<String> image,
        String price,
        double rating,
        String description,
        String town,
        String address,
        int guest
) {
}
