package sportradar.event.card.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import sportradar.event.card.model.CardType;

public record CardPatchRequest(
        @Min(value = 1, message = "Minute must be greater than 0")
        @Max(value = 120, message = "Minute cannot exceed 120")
        Integer minute,

        CardType cardType
) {
}
