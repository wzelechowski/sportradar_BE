package sportradar.event.stadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sportradar.event.stadium.model.Stadium;

import java.util.UUID;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, UUID> {
}
