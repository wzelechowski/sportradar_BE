package sportradar.event.event.repository;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import sportradar.event.competition.model.Competition;
import sportradar.event.event.dto.criteria.EventSearchCriteria;
import sportradar.event.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public record EventSpecification(EventSearchCriteria criteria) implements Specification<Event> {

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Join<Event, Competition> competitionJoin;

        if (query.getResultType() != Long.class) {
            root.fetch("competition", JoinType.LEFT);
        }

        competitionJoin = root.join("competition", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.sportType() != null && !criteria.sportType().isEmpty()) {
            predicates.add(cb.equal(competitionJoin.get("sportType"), criteria.sportType()));
        }

        if (criteria.startDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("eventDate"), criteria.startDate()));
        }

        if (criteria.endDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("eventDate"), criteria.endDate()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
