package sportradar.event.card.dto.request;

import jakarta.validation.constraints.NotNull;
import sportradar.event.card.model.CardType;

public record CardRequest(
        @NotNull
        Integer minute,

        @NotNull
        CardType cardType
) {
}
