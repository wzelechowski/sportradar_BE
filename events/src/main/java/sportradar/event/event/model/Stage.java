package sportradar.event.event.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Stage {
    ROUND_OF_16("ROUND OF 16", 4),
    QUARTER_FINALS("QUARTER FINALS", 5),
    SEMI_FINALS("SEMI FINALS", 6),
    FINAL("FINAL", 7);

    private final String name;
    private final Integer ordering;
}
