package enjoying.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record FeedBackUpdateReq(
        List<String> images,
        String description
) {
}
