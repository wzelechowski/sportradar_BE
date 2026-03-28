package sportradar.event.eventClub.dto.request;

import sportradar.event.card.dto.request.CardRequest;
import sportradar.event.goal.dto.request.GoalRequest;

import java.util.List;
import java.util.UUID;

public record EventClubPatchRequest(
        UUID clubId,
        Boolean isHome,
        Boolean isWinner,
        Integer stagePosition,
        List<GoalRequest> goals,
        List<CardRequest> cards
) {
}
