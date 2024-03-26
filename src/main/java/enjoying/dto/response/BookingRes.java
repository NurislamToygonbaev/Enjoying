package enjoying.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class BookingRes {
    HttpStatus httpStatus;
    String message;
}

