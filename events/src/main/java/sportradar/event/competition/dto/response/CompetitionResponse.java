package sportradar.event.competition.dto.response;

import sportradar.event.competition.model.SportType;

import java.util.UUID;

public record CompetitionResponse(
        UUID id,
        String originId,
        String originName,
        SportType sportType
) {
}
