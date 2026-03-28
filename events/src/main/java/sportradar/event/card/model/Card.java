package sportradar.event.card.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sportradar.event.eventClub.model.EventClub;

import java.util.UUID;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer minute;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType type;

    @ManyToOne
    @JoinColumn(name = "_event_club_id", nullable = false)
    private EventClub eventClub;
}
