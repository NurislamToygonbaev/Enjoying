package enjoying.dto.response;

import enjoying.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record DataAnnouncement(
        List<String> image,
        Long id,
        String price,
        double rating,
        String description,
        Region region,
        String town,
        String address,
        int maxGuest,
        LocalDate checkin,
        LocalDate checkOut) {
}
