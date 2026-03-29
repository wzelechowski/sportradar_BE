package sportradar.event.event.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;
import sportradar.event.eventClub.dto.request.EventClubRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventRequest(
        @NotNull(message = "Season is required")
        Integer season,

        @NotNull(message = "Event date is required")
        LocalDateTime eventDate,

        @NotNull(message = "Event status is required")
        EventStatus eventStatus,

        @NotNull(message = "Stage is required")
        Stage stage,

        @NotNull(message = "Competition ID is required")
        UUID competitionId,

        UUID stadiumId,

        @Valid
        @Size(max = 2, message = "An event can have a maximum of 2 clubs")
        List<EventClubRequest> clubs
) {
}