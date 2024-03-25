package enjoying.dto.response;

import enjoying.enums.HomePrice;
import enjoying.enums.HomeType;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ResultPaginationAnnouncement(Region region,
                                           HomeType homeType,
                                           HouseType houseType,
                                           HomePrice homePrice,
                                           int page,
                                           int size,
                                           List<ForPagination> paginations
) {
}
