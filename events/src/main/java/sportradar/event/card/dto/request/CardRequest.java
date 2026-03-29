package sportradar.event.card.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import sportradar.event.card.model.CardType;

public record CardRequest(
        @NotNull(message = "Minute is required")
        @Min(value = 1, message = "Minute must be greater than 0")
        @Max(value = 120, message = "Minute cannot exceed 120")
        Integer minute,

        @NotNull(message = "Card type is required")
        CardType cardType
) {
}
