package enjoying.dto.request.announcement;

import enjoying.enums.HouseType;
import enjoying.enums.Region;
import enjoying.validation.experience.ExperienceValidation;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record SaveAnnouncementRequest(List<String> images,
                                      HouseType houseType,
                                      @ExperienceValidation
                                      int maxOfQuests,
                                      @ExperienceValidation
                                      Integer price,
                                      @NotBlank
                                      String title,
                                      @NotBlank
                                      String descriptionOfListing,
                                      Region region,
                                      @NotBlank
                                      String town,
                                      @NotBlank
                                      String address
) {
}
