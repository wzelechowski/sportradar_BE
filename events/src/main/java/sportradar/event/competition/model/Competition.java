package sportradar.event.competition.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "competitions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "origin_id", nullable = false, unique = true)
    private String originId;

    @Column(name ="origin_name", nullable = false)
    private String originName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport_type", nullable = false)
    private SportType sportType;
}
