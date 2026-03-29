package sportradar.event.club.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.UUID;

@Entity
@Table(name = "clubs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@BatchSize(size = 100)
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

    @Column(name = "club_country_code", nullable = false)
    private String clubCountryCode;
}
