package sportradar.event.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sportradar.event.event.model.Event;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("""
            SELECT e FROM Event e
            LEFT JOIN FETCH e.stadium
            LEFT JOIN FETCH e.eventClubs
            WHERE e.id = :id
        """)
    Optional<Event> findByIdWithClubs(@Param("id") UUID id);
}
