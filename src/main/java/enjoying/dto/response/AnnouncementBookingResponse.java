package enjoying.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AnnouncementBookingResponse(
        List<String> image,
        Long id,
        String price,
        double rating,
        String description,
        String town,
        String address,
        int guest,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
