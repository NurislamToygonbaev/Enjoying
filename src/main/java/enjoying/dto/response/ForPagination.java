package enjoying.dto.response;

import enjoying.enums.Region;
import lombok.Builder;

import java.util.List;

@Builder
public record ForPagination(Long id,
                            List<String> images,
                            String price,
                            double rating,
                            String description,
                            String address,
                            String town,
                            Region region,
                            String guests) {
}
