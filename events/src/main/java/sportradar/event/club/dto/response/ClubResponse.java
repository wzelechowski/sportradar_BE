package sportradar.event.club.dto.response;

import java.util.UUID;

public record ClubResponse(
        UUID id,
        String name,
        String officialName,
        String slug,
        String abbreviation,
        String clubCountryCode
) {
}
