package sportradar.event.event.model;

import jakarta.persistence.*;
import lombok.*;
import sportradar.event.competition.model.Competition;
import sportradar.event.eventClub.model.EventClub;
import sportradar.event.stadium.model.Stadium;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer season;

    @Column(name = "event_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Builder.Default
    @Column(name = "event_clubs")
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EventClub> eventClubs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_competition_id", nullable = false)
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "_stadium_id")
    private Stadium stadium;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Stage stage;
}
