package sportradar.event.card.dto.request;

import sportradar.event.card.model.CardType;

public record CardPatchRequest(
        Integer minute,
        CardType cardType
) {
}
