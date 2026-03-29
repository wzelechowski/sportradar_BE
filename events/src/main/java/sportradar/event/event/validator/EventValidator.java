package sportradar.event.event.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.event.model.Event;
import sportradar.event.event.model.EventStatus;

import java.time.LocalDateTime;

@Component
public class EventValidator {
    public void validate(Event event) {
        if (event.getEventStatus() == EventStatus.PLAYED) {
            validatePlayedEventRules(event);
        }

        if (event.getEventStatus() == EventStatus.SCHEDULED || event.getEventStatus() == EventStatus.MOVED) {
            validateScheduledOrMovedEventRules(event);
        }

        validateClubRoles(event);
    }

    private void validatePlayedEventRules(Event event) {
        if (event.getEventClubs() != null && event.getEventClubs().size() != 2) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A played event must contains excatly 2 clubs");
        }

        if (event.getEventDate() != null && event.getEventDate().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An event status cannot be PLAYED if the event date is in the future");
        }
    }

    private void validateScheduledOrMovedEventRules(Event event) {
        if (event.getEventDate() != null && event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An event status cannot be SCHEDULED or MOVED if the event date is in the past");
        }
    }

    private void validateClubRoles(Event event) {
        if (event.getEventClubs() != null && event.getEventClubs().size() == 2) {
            long homeClubsCount = event.getEventClubs().stream()
                    .filter(club -> Boolean.TRUE.equals(club.getIsHome()))
                    .count();

            if (homeClubsCount != 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An event must contains exactly one home team and one away team");
            }
        }
    }
}
