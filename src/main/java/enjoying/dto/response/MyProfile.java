package enjoying.dto.response;

import lombok.Builder;

@Builder
public record MyProfile(String name,String phoneNumber) {
}
