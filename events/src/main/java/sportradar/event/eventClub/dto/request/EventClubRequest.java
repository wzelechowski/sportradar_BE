package sportradar.event.eventClub.dto.request;

import jakarta.validation.constraints.NotNull;
import sportradar.event.card.dto.request.CardRequest;
import sportradar.event.goal.dto.request.GoalRequest;

import java.util.List;
import java.util.UUID;

public record EventClubRequest(
        @NotNull
        UUID clubId,

        @NotNull
        Boolean isHome,

        Boolean isWinner,

        Integer stagePosition,

        List<GoalRequest> goals,

        List<CardRequest> cards
) {
}
