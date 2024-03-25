package enjoying.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FavoritesUserResponse(
        String image,
        String fullName,
        String email,
        LocalDate date
) {
}
