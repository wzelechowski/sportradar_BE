package sportradar.event.event.dto.response;

import sportradar.event.competition.dto.response.CompetitionResponse;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventSimplifiedResponse(
        UUID id,
        Integer season,
        EventStatus eventStatus,
        Stage stage,
        LocalDateTime eventDate,
        CompetitionResponse competition,
        String homeClub,
        String awayClub,
        Integer homeGoals,
        Integer awayGoals
) {
}
