package enjoying.dto.response;

import enjoying.enums.Region;

import java.util.List;

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
