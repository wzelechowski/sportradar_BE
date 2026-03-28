package sportradar.event.competition.dto.response;

import java.util.UUID;

public record CompetitionResponse(
        UUID id,
        String originId,
        String originName
) {
}
