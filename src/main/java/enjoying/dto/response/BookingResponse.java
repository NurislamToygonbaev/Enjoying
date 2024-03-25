package enjoying.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Builder
public class BookingResponse {
    HttpStatus httpStatus;
    String message;
//    BigDecimal price;

}
