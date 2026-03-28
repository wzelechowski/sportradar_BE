package sportradar.event.stadium.dto.response;

import java.util.UUID;

public record StadiumResponse(
        UUID id,
        String name,
        Integer capacity
) {
}
