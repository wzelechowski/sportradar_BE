package sportradar.event.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sportradar.event.event.model.Event;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {

    Page<Event> findAll(Pageable pageable);

    @Query("""
            SELECT e FROM Event e
            LEFT JOIN FETCH e.stadium
            LEFT JOIN FETCH e.competition
            LEFT JOIN FETCH e.eventClubs ec
            LEFT JOIN FETCH ec.club
            WHERE e.id = :id
        """)
    Optional<Event> findByIdWithClubs(@Param("id") UUID id);
}
