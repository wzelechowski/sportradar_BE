package sportradar.event.club.dto.request;

public record ClubPatchRequest(
        String name,
        String officialName,
        String slug,
        String abbreviation,
        String countryCode
) {
}
