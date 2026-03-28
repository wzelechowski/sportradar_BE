package sportradar.event.event.dto.request;

import jakarta.validation.constraints.NotNull;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;
import sportradar.event.eventClub.dto.request.EventClubRequest;

import java.util.List;
import java.util.UUID;

public record EventRequest(
        @NotNull
        Integer season,

        @NotNull
        EventStatus eventStatus,

        @NotNull
        Stage stage,

        @NotNull
        UUID competitionId,

        UUID stadiumId,

        @NotNull
        List<EventClubRequest> clubs
) {
}
