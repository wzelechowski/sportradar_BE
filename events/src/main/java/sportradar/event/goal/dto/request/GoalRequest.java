package sportradar.event.goal.dto.request;

import jakarta.validation.constraints.NotNull;

public record GoalRequest(
        @NotNull
        Integer minute
) {
}
