package sportradar.event.stadium.dto.request;

public record StadiumPatchRequest(
        String name,
        Integer capacity
) {
}
