package enjoying.dto.request;

import enjoying.validation.rating.RatingValidation;
import lombok.Builder;

import java.util.List;
@Builder
public record FeedBackSaveRequest(
        @RatingValidation
        Integer rating,
        List<String> images,
        String description
) {
}
