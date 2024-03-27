package enjoying.dto.pagination;

import enjoying.dto.response.ForPagination;
import enjoying.enums.HomePrice;
import enjoying.enums.HomeType;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import lombok.Builder;

import java.util.List;

@Builder
public record ResultSearchAnnouncement(Region region,
                                       HouseType houseType,
                                       int page,
                                       int size,
                                       List<ForPagination> pagination) {
}
