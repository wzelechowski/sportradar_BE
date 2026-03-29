package sportradar.event.goal.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record GoalPatchRequest(
        @Min(value = 1, message = "Minute must be greater than 0")
        @Max(value = 120, message = "Minute cannot exceed 120")
        Integer minute
) {
}
