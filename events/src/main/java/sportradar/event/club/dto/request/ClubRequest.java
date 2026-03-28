package sportradar.event.club.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClubRequest(
        @NotBlank
        String name,

        @NotBlank
        String officialName,

        @NotBlank
        String slug,

        @NotBlank
        String abbreviation,

        @NotBlank
        String countryCode,

        Integer stagePosition
) {
}
