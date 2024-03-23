package enjoying.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record FindFeedBackResponse(
        Integer rating,
        List<String> images,
        String description,
        int likes,
        int disLikes
) {
}
