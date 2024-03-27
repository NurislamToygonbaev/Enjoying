package enjoying.dto.request;

import enjoying.enums.HomePrice;
import enjoying.enums.HouseType;
import enjoying.validation.experience.ExperienceValidation;
import enjoying.validation.rating.RatingValidation;

import java.util.List;

public record MyAnnounceRequest(List<HouseType> houseTypes,
                                HomePrice homePrice,
                                @RatingValidation
                                int rating,
                                @ExperienceValidation
                                int page,
                                @ExperienceValidation
                                int size
)
{}
