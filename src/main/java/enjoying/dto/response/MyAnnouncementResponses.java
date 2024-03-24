package enjoying.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record MyAnnouncementResponses(
        List<String> image,
        Long id,
        BigDecimal price,
        double rating,
        String description,
        String town,
        String address,
        int guest,
        int likeSize
) {
}
