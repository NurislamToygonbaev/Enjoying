package enjoying.dto.request;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record Bookingrequest(LocalDate checkin,LocalDate checkout) {

}
