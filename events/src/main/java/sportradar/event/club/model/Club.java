package sportradar.event.club.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "clubs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(name = "official_name", nullable = false)
    private String officialName;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String abbreviation;

    @Column(name = "team_country_code", nullable = false)
    private String teamCountryCode;
}
