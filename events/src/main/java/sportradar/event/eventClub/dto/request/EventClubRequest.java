package sportradar.event.eventClub.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import sportradar.event.card.dto.request.CardRequest;
import sportradar.event.goal.dto.request.GoalRequest;

import java.util.List;
import java.util.UUID;

public record EventClubRequest(
        @NotNull(message = "Club id is required")
        UUID clubId,

        @NotNull(message = "isHome flag is required")
        Boolean isHome,

        Boolean isWinner,

        @Min(value = 1, message = "Stage position must be greater than 0")
        Integer stagePosition,

        @Valid
        List<GoalRequest> goals,

        @Valid
        List<CardRequest> cards
) {
}
