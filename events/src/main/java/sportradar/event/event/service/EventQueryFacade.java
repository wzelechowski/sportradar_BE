package sportradar.event.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sportradar.event.event.model.Event;
import sportradar.event.event.repository.EventRepository;
import sportradar.event.eventClub.model.EventClub;
import sportradar.event.eventClub.repository.EventClubRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventQueryFacade {
    private final EventRepository eventRepository;
    private final EventClubRepository eventClubRepository;

    @Transactional(readOnly = true)
    public Event getEventWithDetails(UUID id) {
        Event event = eventRepository.findByIdWithClubs(id).orElseThrow();

        List<EventClub> eventClubs = event.getEventClubs();
        if (!eventClubs.isEmpty()) {
            eventClubRepository.fetchGoalsForClubs(eventClubs);
            eventClubRepository.fetchCardsForClubs(eventClubs);
        }

        return event;
    }
}
