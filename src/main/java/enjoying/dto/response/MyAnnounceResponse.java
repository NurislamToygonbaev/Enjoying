package enjoying.dto.response;

import enjoying.enums.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class MyAnnounceResponse {
    private Long id;
    private int rentInfoSize;
    private int likeSize;
    private List<String> images;
    private String price;
    private double rating;
    private String description;
    private String address;
    private String town;
    private Region region;
    private String guest;
}
