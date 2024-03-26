package enjoying.dto.request;

import enjoying.enums.HomePrice;
import enjoying.enums.HomeType;
import enjoying.enums.HouseType;
import enjoying.enums.Region;

public record PaginationRequest(Region region,
                                HomeType homeType,
                                HouseType houseType,
                                HomePrice homePrice,
                                int page,
                                int size) {
}
