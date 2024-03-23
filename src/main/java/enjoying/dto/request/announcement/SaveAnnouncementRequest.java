package enjoying.dto.request.announcement;

import enjoying.enums.HouseType;
import enjoying.enums.Region;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record SaveAnnouncementRequest(List<String> images,
                                      HouseType houseType,
                                      int maxOfQuests,
                                      BigDecimal price,
                                      String title,
                                      String descriptionOfListing,
                                      Region region,
                                      String town,
                                      String address
) {}
