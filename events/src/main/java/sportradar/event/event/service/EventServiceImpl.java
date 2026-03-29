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
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.repository.EventRepository;
import sportradar.event.event.validator.EventValidator;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventQueryFacade eventQueryFacade;
    private final EntityManager entityManager;
    private final EventValidator eventValidator;

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
        eventValidator.validate(event);
        calculateWinner(event);
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
        if (!event.getEventStatus().canTransitionTo(request.eventStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change event status from" + event.getEventStatus() + " to " + request.eventStatus());
        }

        eventMapper.updateEntity(event, request);
        eventValidator.validate(event);
        calculateWinner(event);
        event = eventRepository.saveAndFlush(event);
        entityManager.clear();
        Event fetchedEvent = eventQueryFacade.getEventWithDetails(event.getId());
        return eventMapper.toResponse(fetchedEvent);
    }

    @Override
    @Transactional
    public EventResponse patch(UUID id, EventPatchRequest request) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        if (!event.getEventStatus().canTransitionTo(request.eventStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change event status from" + event.getEventStatus() + " to " + request.eventStatus());
        }

        eventMapper.patchEntity(event, request);
        eventValidator.validate(event);
        calculateWinner(event);
        event = eventRepository.saveAndFlush(event);
        entityManager.clear();
        Event fetchedEvent = eventQueryFacade.getEventWithDetails(event.getId());
        return eventMapper.toResponse(fetchedEvent);
    }

    private void calculateWinner(Event event) {
        if (event.getEventStatus() != EventStatus.PLAYED || event.getEventClubs() == null || event.getEventClubs().size() != 2) {
            if (event.getEventClubs() != null) {
                event.getEventClubs().forEach(club -> club.setIsWinner(null));
            }

            return;
        }

        var club1 = event.getEventClubs().getFirst();
        var club2 = event.getEventClubs().getLast();

        int score1 = club1.getGoals() != null ? club1.getGoals().size() : 0;
        int score2 = club2.getGoals() != null ? club2.getGoals().size() : 0;

        if (score1 > score2) {
            club1.setIsWinner(true);
            club2.setIsWinner(false);
        } else if (score2 > score1) {
            club1.setIsWinner(false);
            club2.setIsWinner(true);
        } else {
            club1.setIsWinner(false);
            club2.setIsWinner(false);
        }
    }
}
