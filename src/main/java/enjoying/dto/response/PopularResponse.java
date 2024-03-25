package enjoying.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import enjoying.enums.Region;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class PopularResponse{
    @JsonProperty("photo")
    private String photo;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("region")
    private Region region;

    @JsonProperty("town")
    private String town;

    @JsonProperty("address")
    private String address;    }



