package enjoying.dto.response;

import lombok.Builder;

@Builder
public record FindAllResponse(String fulName,String email,int announcement,int booking) {
}
