package enjoying.dto.request;

import enjoying.enums.HomePrice;
import enjoying.enums.HouseType;

import java.util.List;

public record MyAnnounceRequest(List<HouseType> houseTypes,
                                HomePrice homePrice,
                                int rating,
                                int page,
                                int size
)
{}
