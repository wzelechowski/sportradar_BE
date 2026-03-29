package sportradar.event.event.dto.response;

import sportradar.event.competition.dto.response.CompetitionResponse;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;
import sportradar.event.eventClub.dto.response.EventClubResponse;
import sportradar.event.stadium.dto.response.StadiumResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventResponse(
        UUID id,
        Integer season,
        EventStatus eventStatus,
        Stage stage,
        LocalDateTime eventDate,
        CompetitionResponse competition,
        StadiumResponse stadium,
        List<EventClubResponse> eventClubs
) {
}
