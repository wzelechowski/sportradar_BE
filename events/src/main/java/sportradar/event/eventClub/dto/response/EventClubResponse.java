package sportradar.event.eventClub.dto.response;

import sportradar.event.card.dto.request.CardRequest;
import sportradar.event.club.dto.response.ClubResponse;
import sportradar.event.goal.dto.request.GoalRequest;

import java.util.List;
import java.util.UUID;

public record EventClubResponse(
        UUID id,
        ClubResponse club,
        Boolean isHome,
        Boolean isWinner,
        Integer stagePosition,
        List<GoalRequest> goals,
        List<CardRequest> cards
) {
}
