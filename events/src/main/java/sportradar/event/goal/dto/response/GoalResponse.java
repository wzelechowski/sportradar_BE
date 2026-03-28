package sportradar.event.goal.dto.response;

import java.util.UUID;

public record GoalResponse(
        UUID id,
        Integer minute
) {
}
