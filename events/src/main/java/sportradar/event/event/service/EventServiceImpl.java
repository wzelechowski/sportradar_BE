package sportradar.event.event.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.event.dto.request.EventPatchRequest;
import sportradar.event.event.dto.request.EventRequest;
import sportradar.event.event.dto.response.EventResponse;
import sportradar.event.event.mapper.EventMapper;
import sportradar.event.event.model.Event;
import sportradar.event.event.repository.EventRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventQueryFacade eventQueryFacade;
    private final EntityManager entityManager;

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toResponse)
                .toList();
    }

    @Override
    public EventResponse getEventById(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponse getEventWithDetails(UUID id) {
        Event event = eventQueryFacade.getEventWithDetails(id);
        return eventMapper.toResponse(event);
    }

    @Override
    @Transactional
    public EventResponse create(EventRequest request) {
        Event event = eventMapper.toEntity(request);
        event = eventRepository.saveAndFlush(event);
        entityManager.clear();
        Event fetchedEvent = eventQueryFacade.getEventWithDetails(event.getId());
        return eventMapper.toResponse(fetchedEvent);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        eventRepository.delete(event);
    }

    @Override
    @Transactional
    public EventResponse update(UUID id, EventRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        eventMapper.updateEntity(event, request);
        event = eventRepository.saveAndFlush(event);
        entityManager.clear();
        Event fetchedEvent = eventQueryFacade.getEventWithDetails(event.getId());
        return eventMapper.toResponse(fetchedEvent);
    }

    @Override
    @Transactional
    public EventResponse patch(UUID id, EventPatchRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        eventMapper.patchEntity(event, request);
        event = eventRepository.saveAndFlush(event);
        entityManager.clear();
        Event fetchedEvent = eventQueryFacade.getEventWithDetails(event.getId());
        return eventMapper.toResponse(fetchedEvent);
    }
}
