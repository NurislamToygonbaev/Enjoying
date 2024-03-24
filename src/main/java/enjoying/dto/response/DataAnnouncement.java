package enjoying.dto.response;

import enjoying.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record DataAnnouncement(String image,
                               BigDecimal price,
                               double rating,
                               String title,
                               Region region,
                               String town,
                               String address,
                               int maxGuest,
                               LocalDate checkin,
                               LocalDate checkOut) {
}
