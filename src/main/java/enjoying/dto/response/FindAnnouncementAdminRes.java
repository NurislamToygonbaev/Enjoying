package enjoying.dto.response;

import enjoying.enums.HouseType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.util.List;

@Builder
public record FindAnnouncementAdminRes(
        Long id,
        List<String> images,
        @Enumerated(EnumType.STRING)
        HouseType houseType,
        int guest,
        String title,
        String address,
        String town,
        String description,
        String image,
        String fullName,
        String email
) {
}
