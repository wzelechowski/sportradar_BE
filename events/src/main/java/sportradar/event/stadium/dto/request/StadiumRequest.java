package sportradar.event.stadium.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StadiumRequest(
        @NotBlank
        String name,

        @NotNull
        Integer capacity
) {
}
