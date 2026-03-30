package sportradar.event.event;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.event.dto.criteria.EventSearchCriteria;
import sportradar.event.event.dto.request.EventPatchRequest;
import sportradar.event.event.dto.request.EventRequest;
import sportradar.event.event.dto.response.EventResponse;
import sportradar.event.event.dto.response.EventSimplifiedResponse;
import sportradar.event.event.mapper.EventMapper;
import sportradar.event.event.model.Event;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.repository.EventRepository;
import sportradar.event.event.service.EventQueryFacade;
import sportradar.event.event.service.EventServiceImpl;
import sportradar.event.event.validator.EventValidator;
import sportradar.event.eventClub.model.EventClub;
import sportradar.event.goal.model.Goal;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Mock
    private EventQueryFacade eventQueryFacade;

    @Mock
    private EventValidator eventValidator;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event createSampleEvent(EventStatus status) {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setEventStatus(status);

        EventClub homeClub = new EventClub();
        homeClub.setIsHome(true);
        homeClub.setGoals(List.of(new Goal(), new Goal()));

        EventClub awayClub = new EventClub();
        awayClub.setIsHome(false);
        awayClub.setGoals(List.of(new Goal()));

        event.setEventClubs(List.of(homeClub, awayClub));
        return event;
    }

    @Test
    @DisplayName("Should return simplified event by ID")
    void shouldReturnSimplifiedEventById() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        UUID eventId = UUID.randomUUID();
        EventSimplifiedResponse response = mock(EventSimplifiedResponse.class);

        when(eventRepository.findByIdWithClubs(eventId)).thenReturn(Optional.of(event));
        when(eventMapper.toSimplifiedResponse(event)).thenReturn(response);

        EventSimplifiedResponse result = eventService.getEventById(eventId);

        assertNotNull(result);
        verify(eventRepository, times(1)).findByIdWithClubs(eventId);
    }

    @Test
    @DisplayName("Should throw 404 NOT FOUND when event does not exist")
    void shouldThrowNotFoundWhenEventDoesNotExist() {
        UUID eventId = UUID.randomUUID();

        when(eventRepository.findByIdWithClubs(eventId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.getEventById(eventId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("Should create event and calculate home winner")
    void shouldCreateEventAndCalculateHomeWinner() {
        EventRequest request = mock(EventRequest.class);
        Event event = createSampleEvent(EventStatus.PLAYED);
        EventResponse response = mock(EventResponse.class);

        when(eventMapper.toEntity(request)).thenReturn(event);
        when(eventRepository.saveAndFlush(any(Event.class))).thenReturn(event);
        when(eventQueryFacade.getEventWithDetails(event.getId())).thenReturn(event);
        when(eventMapper.toResponse(event)).thenReturn(response);

        EventResponse result = eventService.create(request);

        assertNotNull(result);
        assertTrue(event.getEventClubs().getFirst().getIsWinner());
        assertFalse(event.getEventClubs().getLast().getIsWinner());
        verify(eventValidator, times(1)).validate(event);
        verify(eventRepository, times(1)).saveAndFlush(event);
    }

    @Test
    @DisplayName("Should not calculate winner if event is not played")
    void shouldNotCalculateWinnerIfEventIsNotPlayed() {
        EventRequest request = mock(EventRequest.class);
        Event event = createSampleEvent(EventStatus.SCHEDULED);

        when(eventMapper.toEntity(request)).thenReturn(event);
        when(eventRepository.saveAndFlush(any(Event.class))).thenReturn(event);
        when(eventQueryFacade.getEventWithDetails(event.getId())).thenReturn(event);

        eventService.create(request);

        assertNull(event.getEventClubs().getFirst().getIsWinner());
        assertNull(event.getEventClubs().getLast().getIsWinner());
    }

    @Test
    @DisplayName("Should delete event successfully")
    void shouldDeleteEvent() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        eventService.delete(eventId);

        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    @DisplayName("Should throw 404 NOT FOUND when deleting non-existent event")
    void shouldThrowNotFoundWhenDeletingNonExistentEvent() {
        UUID eventId = UUID.randomUUID();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.delete(eventId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertNotNull(exception.getReason());
        assertTrue(exception.getReason().contains("Event not found"));
        verify(eventRepository, never()).delete(any(Event.class));
    }

    @Test
    @DisplayName("Should throw 400 BAD REQUEST when status transition is invalid during update")
    void shouldThrowExceptionWhenStatusTransitionIsInvalid() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setEventStatus(EventStatus.CANCELLED);
        EventRequest request = mock(EventRequest.class);

        when(request.eventStatus()).thenReturn(EventStatus.PLAYED);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.update(eventId, request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("Cannot change event status"));
        verify(eventRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should update event successfully")
    void shouldUpdateEventSuccessfully() {
        UUID eventId = UUID.randomUUID();
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        EventRequest request = mock(EventRequest.class);
        EventResponse responseDto = mock(EventResponse.class);

        when(request.eventStatus()).thenReturn(EventStatus.SCHEDULED);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.saveAndFlush(any(Event.class))).thenReturn(event);
        when(eventQueryFacade.getEventWithDetails(event.getId())).thenReturn(event);
        when(eventMapper.toResponse(event)).thenReturn(responseDto);

        EventResponse result = eventService.update(eventId, request);

        assertNotNull(result);
        verify(eventMapper, times(1)).updateEntity(event, request);
        verify(eventValidator, times(1)).validate(event);
        verify(eventRepository, times(1)).saveAndFlush(event);
    }

    @Test
    @DisplayName("Should throw 404 NOT FOUND when updating non-existent event")
    void shouldThrowNotFoundWhenUpdatingNonExistentEvent() {
        UUID eventId = UUID.randomUUID();
        EventRequest request = mock(EventRequest.class);

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.update(eventId, request));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(eventMapper, never()).updateEntity(any(), any());
        verify(eventRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should patch event successfully")
    void shouldPatchEventSuccessfully() {
        UUID eventId = UUID.randomUUID();
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        EventPatchRequest patchRequest = mock(EventPatchRequest.class);
        EventResponse responseDto = mock(EventResponse.class);

        when(patchRequest.eventStatus()).thenReturn(EventStatus.SCHEDULED);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventRepository.saveAndFlush(any(Event.class))).thenReturn(event);
        when(eventQueryFacade.getEventWithDetails(event.getId())).thenReturn(event);
        when(eventMapper.toResponse(event)).thenReturn(responseDto);

        EventResponse result = eventService.patch(eventId, patchRequest);

        assertNotNull(result);
        verify(eventMapper, times(1)).patchEntity(event, patchRequest);
        verify(eventValidator, times(1)).validate(event);
        verify(eventRepository, times(1)).saveAndFlush(event);
    }

    @Test
    @DisplayName("Should throw 400 BAD REQUEST when patching with invalid status transition")
    void shouldThrowExceptionWhenPatchingWithInvalidStatusTransition() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setEventStatus(EventStatus.CANCELLED);
        EventPatchRequest patchRequest = mock(EventPatchRequest.class);

        when(patchRequest.eventStatus()).thenReturn(EventStatus.PLAYED);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.patch(eventId, patchRequest));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        verify(eventMapper, never()).patchEntity(any(), any());
        verify(eventRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should throw 404 NOT FOUND when patching non-existent event")
    void shouldThrowNotFoundWhenPatchingNonExistentEvent() {
        UUID eventId = UUID.randomUUID();
        EventPatchRequest patchRequest = mock(EventPatchRequest.class);

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.patch(eventId, patchRequest));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(eventMapper, never()).patchEntity(any(), any());
        verify(eventRepository, never()).saveAndFlush(any());
    }

    @Test
    @DisplayName("Should return detailed event response when event exists")
    void shouldReturnDetailedEvent() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        EventResponse responseDto = mock(EventResponse.class);

        when(eventQueryFacade.getEventWithDetails(eventId)).thenReturn(event);
        when(eventMapper.toResponse(event)).thenReturn(responseDto);

        EventResponse result = eventService.getEventWithDetails(eventId);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(eventQueryFacade, times(1)).getEventWithDetails(eventId);
        verify(eventMapper, times(1)).toResponse(event);
    }

    @Test
    @DisplayName("Should throw exception when getting details for non-existent event via facade")
    void shouldThrowExceptionWhenGettingDetailsForNonExistentEvent() {
        UUID eventId = UUID.randomUUID();

        when(eventQueryFacade.getEventWithDetails(eventId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> eventService.getEventWithDetails(eventId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(eventMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Should return page of simplified events matching criteria")
    void shouldReturnPageOfSimplifiedEvents() {
        EventSearchCriteria criteria = mock(EventSearchCriteria.class);
        Pageable pageable = PageRequest.of(0, 10);
        Event event = new Event();
        Page<Event> eventPage = new PageImpl<>(List.of(event));
        EventSimplifiedResponse simplifiedResponse = mock(EventSimplifiedResponse.class);

        when(eventRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(eventPage);
        when(eventMapper.toSimplifiedResponse(event)).thenReturn(simplifiedResponse);

        Page<EventSimplifiedResponse> result = eventService.getAllEvents(criteria, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(simplifiedResponse, result.getContent().getFirst());
        verify(eventRepository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(eventMapper, times(1)).toSimplifiedResponse(event);
    }

    @Test
    @DisplayName("Should return empty page when no events match criteria")
    void shouldReturnEmptyPageWhenNoEventsMatch() {
        EventSearchCriteria criteria = mock(EventSearchCriteria.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Event> emptyPage = new PageImpl<>(List.of());

        when(eventRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(emptyPage);

        Page<EventSimplifiedResponse> result = eventService.getAllEvents(criteria, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(eventMapper, never()).toSimplifiedResponse(any());
    }
}