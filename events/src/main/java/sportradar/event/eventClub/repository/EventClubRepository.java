package sportradar.event.eventClub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sportradar.event.eventClub.model.EventClub;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventClubRepository extends JpaRepository<EventClub, UUID> {

    @Query("""
        SELECT ec FROM EventClub ec
        LEFT JOIN FETCH ec.goals
        WHERE ec IN :clubs
    """)
    List<EventClub> fetchGoalsForClubs(@Param("clubs") List<EventClub> clubs);

    @Query("""
        SELECT ec FROM EventClub ec
        LEFT JOIN FETCH ec.cards
        WHERE ec IN :clubs
    """)
    List<EventClub> fetchCardsForClubs(@Param("clubs") List<EventClub> clubs);
}
