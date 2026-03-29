package sportradar.event.event.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sportradar.event.event.dto.criteria.EventSearchCriteria;
import sportradar.event.event.dto.request.EventPatchRequest;
import sportradar.event.event.dto.request.EventRequest;
import sportradar.event.event.dto.response.EventResponse;
import sportradar.event.event.dto.response.EventSimplifiedResponse;

import java.util.UUID;

public interface EventService {

    Page<EventSimplifiedResponse> getAllEvents(EventSearchCriteria criteria, Pageable pageable);

    EventSimplifiedResponse getEventById(UUID id);

    EventResponse getEventWithDetails(UUID id);

    EventResponse create(EventRequest request);

    void delete(UUID id);

    EventResponse update(UUID id, EventRequest request);

    EventResponse patch(UUID id, EventPatchRequest request);
}
