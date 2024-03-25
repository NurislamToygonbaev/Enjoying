package enjoying.dto.request;

import enjoying.enums.HomePrice;
import enjoying.enums.HomeType;
import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.validation.experience.ExperienceValidation;

public record PaginationRequest(Region region,
                                HomeType homeType,
                                HouseType houseType,
                                HomePrice homePrice,
                                @ExperienceValidation
                                int page,
                                @ExperienceValidation
                                int size) {
}
