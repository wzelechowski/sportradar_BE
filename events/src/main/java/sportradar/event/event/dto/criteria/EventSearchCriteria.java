package sportradar.event.event.dto.criteria;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record EventSearchCriteria(
        String sportType,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startDate,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime endDate
) {
}
