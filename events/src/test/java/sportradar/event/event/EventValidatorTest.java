package sportradar.event.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.club.model.Club;
import sportradar.event.event.model.Event;
import sportradar.event.event.model.EventStatus;
import sportradar.event.event.model.Stage;
import sportradar.event.event.validator.EventValidator;
import sportradar.event.eventClub.model.EventClub;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EventValidatorTest {

    private final EventValidator validator = new EventValidator();

    private Event createSampleEvent(EventStatus eventStatus) {
        Event event = new Event();
        event.setEventStatus(eventStatus);
        event.setEventDate(LocalDateTime.now().plusDays(7));
        event.setSeason(LocalDateTime.now().getYear());
        event.setStage(Stage.FINAL);

        Club homeClub = new Club();
        homeClub.setId(UUID.randomUUID());

        Club awayClub = new Club();
        awayClub.setId(UUID.randomUUID());

        EventClub home = new EventClub();
        home.setIsHome(true);
        home.setClub(homeClub);

        EventClub away = new EventClub();
        away.setIsHome(false);
        away.setClub(awayClub);

        event.setEventClubs(List.of(home, away));

        return event;
    }

    @Test
    @DisplayName("Should throw exception when PLAYED event is in the future")
    void shouldThrowExceptionWhenPlayedEventIsInTheFuture() {
        Event event = createSampleEvent(EventStatus.PLAYED);
        event.setEventDate(LocalDateTime.now().plusDays(1));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("future"));
    }

    @Test
    @DisplayName("Should throw exception when two home teams")
    void shouldThrowExceptionWhenTwoHomeTeams() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.getEventClubs().getFirst().setIsHome(true);
        event.getEventClubs().getLast().setIsHome(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("one home team"));
    }

    @Test
    @DisplayName("Should throw exception when played event has wrong club count")
    void shouldThrowExceptionWhenPlayedEventHasWrongClubCount() {
        Event event = createSampleEvent(EventStatus.PLAYED);
        event.setEventClubs(List.of(new EventClub()));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("exactly 2 clubs"));
    }

    @Test
    @DisplayName("Should throw exception when scheduled event is in the past")
    void shouldThrowExceptionWhenScheduledEventIsInPast() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.setEventDate(LocalDateTime.now().minusDays(1));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("past"));
    }

    @Test
    @DisplayName("Should throw exception when moved event is in the past")
    void shouldThrowExceptionWhenMovedEventIsInPast() {
        Event event = createSampleEvent(EventStatus.MOVED);
        event.setEventDate(LocalDateTime.now().minusHours(5));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("past"));
    }

    @Test
    @DisplayName("Should throw exception when no home team is defined")
    void shouldThrowExceptionWhenNoHomeTeam() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.getEventClubs().getFirst().setIsHome(false);
        event.getEventClubs().getLast().setIsHome(false);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("one home team"));
    }

    @Test
    @DisplayName("Should not throw exception for valid event")
    void shouldNotThrowExceptionForValidEvent() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        assertDoesNotThrow(() -> validator.validate(event));
    }

    @Test
    @DisplayName("Should pass when event date is in the same year as season")
    void shouldPassWhenEventDateIsSameAsSeason() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        assertDoesNotThrow(() -> validator.validate(event));
    }

    @Test
    @DisplayName("Should pass when event date is in the year following the season start")
    void shouldPassWhenEventDateIsNextYear() {
        int currentYear = LocalDateTime.now().getYear();
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.setSeason(currentYear);
        event.setEventDate(LocalDateTime.of(currentYear + 1, 3, 15, 20, 0));
        assertDoesNotThrow(() -> validator.validate(event));
    }

    @Test
    @DisplayName("Should throw exception when event date is before season start year")
    void shouldThrowExceptionWhenDateIsBeforeSeason() {
        int futureYear = LocalDateTime.now().getYear() + 2;
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.setSeason(futureYear);
        event.setEventDate(LocalDateTime.now().plusDays(1));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("out of range"));
    }

    @Test
    @DisplayName("Should throw exception when event date is more than one year after season start")
    void shouldThrowExceptionWhenDateIsTooFarInFuture() {
        int currentYear = LocalDateTime.now().getYear();
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.setSeason(currentYear);
        event.setEventDate(LocalDateTime.now().plusYears(3));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("out of range"));
    }

    @Test
    @DisplayName("Should skip season validation if date or season is null")
    void shouldSkipSeasonValidationIfDataIsMissing() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.setSeason(null);
        assertDoesNotThrow(() -> validator.validate(event));
    }

    @Test
    @DisplayName("Should throw excpetion when clubs are not unique")
    void shouldThrowExceptionWhenClubsAreNotUnique() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        Club club = event.getEventClubs().getFirst().getClub();
        event.getEventClubs().getLast().setClub(club);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> validator.validate(event));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(Objects.requireNonNull(exception.getReason()).contains("unique clubs"));
    }

    @Test
    @DisplayName("Should not throw exception when event clubs are missing for scheduled event")
    void shouldNotThrowExceptionWhenClubsAreNull() {
        Event event = createSampleEvent(EventStatus.SCHEDULED);
        event.setEventClubs(null);
        assertDoesNotThrow(() -> validator.validate(event));
    }
}