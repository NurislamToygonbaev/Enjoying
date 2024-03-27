package enjoying.dto.request;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.validation.experience.ExperienceValidation;
import lombok.Builder;

@Builder
public record SearchRequest(
        Region region,
        String city,
        HouseType houseType,
        @ExperienceValidation
        int page,
        @ExperienceValidation
        int size
) {
}
