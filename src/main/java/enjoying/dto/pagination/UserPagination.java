package enjoying.dto.pagination;

import enjoying.dto.response.AnnouncementResponses;
import lombok.Builder;

import java.util.List;

@Builder
public record UserPagination(
        int page,
        int size,
        List<AnnouncementResponses> responses
) {
}
