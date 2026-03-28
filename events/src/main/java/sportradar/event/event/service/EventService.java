package sportradar.event.event.service;

import sportradar.event.event.dto.request.EventPatchRequest;
import sportradar.event.event.dto.request.EventRequest;
import sportradar.event.event.dto.response.EventResponse;

import java.util.List;
import java.util.UUID;

public interface EventService {
    List<EventResponse> getAllEvents();

    EventResponse getEventById(UUID id);

    EventResponse getEventWithDetails(UUID id);

    EventResponse create(EventRequest request);

    void delete(UUID id);

    EventResponse update(UUID id, EventRequest request);

    EventResponse patch(UUID id, EventPatchRequest request);
}
