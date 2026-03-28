package sportradar.event.competition.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CompetitionRequest(
        @NotBlank
        String originId,

        @NotBlank
        String originName
) {
}
