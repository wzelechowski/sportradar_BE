package sportradar.event.stadium.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "stadiums")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;
}
