package sportradar.event.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClubRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
        String name,

        @NotBlank(message = "Official name is required")
        @Size(min = 2, max = 255, message = "Official name must be between 2 and 255 characters")
        String officialName,

        @NotBlank(message = "Slug is required")
        @Pattern(
                regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
                message = "Slug must contain only lowercase letters and numbers, separated by single hyphens"
        )
        @Size(max = 255, message = "Slug cannot exceed 255 characters")
        String slug,

        @NotBlank(message = "Abbreviation is required")
        @Pattern(
                regexp = "^[A-Z0-9]{2,4}$",
                message = "Abbreviation must be between 2 and 4 uppercase letters or numbers"
        )
        String abbreviation,

        @NotBlank(message = "Country code is required")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Country code must be exactly 3 uppercase letters"
        )
        String clubCountryCode
) {
}