package enjoying.dto.request;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.validation.experience.ExperienceValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record EditAnnouncementReq(
        List<String> images,
        HouseType houseType,
        @ExperienceValidation
        @NotNull
        Integer maxOfQuests,
        @ExperienceValidation
        @NotNull
        Integer price,
        String title,
        String description,
        Region region,
        String town,
        String address
) {
}
