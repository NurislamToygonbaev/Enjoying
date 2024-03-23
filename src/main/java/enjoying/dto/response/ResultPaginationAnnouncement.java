package enjoying.dto.response;

import enjoying.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ResultPaginationAnnouncement(int page,
                                           int size,
                                           List<ForPagination> paginations
) {
}
