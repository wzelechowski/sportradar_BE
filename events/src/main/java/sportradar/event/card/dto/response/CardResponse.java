package sportradar.event.card.dto.response;

import sportradar.event.card.model.CardType;

import java.util.UUID;

public record CardResponse(
        UUID id,
        Integer minute,
        CardType cardType
) {
}
