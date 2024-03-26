package enjoying.dto.response;

import lombok.Builder;
import java.time.LocalDate;
@Builder
public record BookingResponse(
        LocalDate checkIn,
        LocalDate checkOut,
        String image,
        String fullName,
        String email
) {
}
