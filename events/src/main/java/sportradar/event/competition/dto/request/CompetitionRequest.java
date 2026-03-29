package sportradar.event.competition.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sportradar.event.competition.model.SportType;

public record CompetitionRequest(
        @NotBlank(message = "Origin id is required")
        @Size(min = 2, max = 255, message = "Origin id must be between 2 and 255 characters")
        String originId,

        @NotBlank(message = "Origin name is required")
        @Size(min = 2, max = 255, message = "Origin name must be between 2 and 255 characters")
        String originName,

        @NotNull(message = "Sport type is required")
        SportType sportType
) {
}
