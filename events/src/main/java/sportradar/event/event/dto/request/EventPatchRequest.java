package sportradar.event.event.dto.request;

import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;
import sportradar.event.eventClub.dto.request.EventClubRequest;

import java.util.List;
import java.util.UUID;

public record EventPatchRequest(
        Integer season,
        EventStatus eventStatus,
        Stage stage,
        UUID competitionId,
        UUID stadiumId,
        List<EventClubRequest> clubs
) {
}
