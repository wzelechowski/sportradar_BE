package sportradar.event.goal.model;

import jakarta.persistence.*;
import lombok.*;
import sportradar.event.eventClub.model.EventClub;

import java.util.UUID;

@Entity
@Table(name = "goals")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer minute;

    @ManyToOne
    @JoinColumn(name = "_event_club_id", nullable = false)
    private EventClub eventClub;
}
