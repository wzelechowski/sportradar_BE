package sportradar.event.event.dto.criteria;

import java.time.LocalDateTime;

public record EventSearchCriteria(
        String sportType,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
