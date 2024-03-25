package enjoying.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AllFeedBackResponse(
        String image,
        String fullName,
        LocalDate date,
        int count,
        String description,
        int likes,
        int disLikes
) {
}
