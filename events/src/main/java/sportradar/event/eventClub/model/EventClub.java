package sportradar.event.eventClub.model;

import jakarta.persistence.*;
import lombok.*;
import sportradar.event.card.model.Card;
import sportradar.event.club.model.Club;
import sportradar.event.event.model.Event;
import sportradar.event.goal.model.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "event_clubs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventClub {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_club_id", nullable = false)
    private Club club;

    @Column(name = "is_home", nullable = false)
    private Boolean isHome;

    @Column(name = "is_winner")
    private Boolean isWinner;

    @Column(name = "stage_position")
    private Integer stagePosition;

    @Builder.Default
    @OneToMany(mappedBy = "eventClub", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Goal> goals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "eventClub", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_event_id", nullable = false)
    private Event event;
}
