package sportradar.event.event.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;
import sportradar.event.eventClub.dto.request.EventClubRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventPatchRequest(
        Integer season,

        LocalDateTime eventDate,

        EventStatus eventStatus,

        Stage stage,

        UUID competitionId,

        UUID stadiumId,

        @Valid
        @Size(max = 2, message = "An event can have a maximum of 2 clubs")
        List<EventClubRequest> clubs
) {
}
