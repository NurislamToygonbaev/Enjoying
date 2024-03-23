package enjoying.dto.request;

import java.time.LocalDate;
import java.util.List;

public record FeedBackSaveRequest(
        Integer rating,
        List<String> images,
        String description
) {
}
