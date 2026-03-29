package sportradar.event.competition.dto.request;

import jakarta.validation.constraints.Size;
import sportradar.event.competition.model.SportType;

public record CompetitionPatchRequest(
        @Size(min = 2, max = 255, message = "Origin id must be between 2 and 255 characters")
        String originId,

        @Size(min = 2, max = 255, message = "Origin name must be between 2 and 255 characters")
        String originName,

        SportType sportType
) {
}
