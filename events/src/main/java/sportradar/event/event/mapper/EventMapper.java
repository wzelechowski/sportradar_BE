package sportradar.event.event.mapper;

import org.mapstruct.*;
import sportradar.event.competition.mapper.CompetitionMapper;
import sportradar.event.event.dto.request.EventPatchRequest;
import sportradar.event.event.dto.request.EventRequest;
import sportradar.event.event.dto.response.EventResponse;
import sportradar.event.event.dto.response.EventSimplifiedResponse;
import sportradar.event.event.model.Event;
import sportradar.event.eventClub.mapper.EventClubMapper;
import sportradar.event.eventClub.model.EventClub;
import sportradar.event.stadium.mapper.StadiumMapper;

@Mapper(componentModel = "spring", uses = {EventClubMapper.class, StadiumMapper.class, CompetitionMapper.class}, builder = @Builder(disableBuilder = true))
public interface EventMapper {

    EventResponse toResponse(Event event);

    @Mapping(target = "homeClub", expression = "java(getHomeClubName(event))")
    @Mapping(target = "awayClub", expression = "java(getAwayClubName(event))")
    @Mapping(target = "homeGoals", expression = "java(getHomeGoals(event))")
    @Mapping(target = "awayGoals", expression = "java(getAwayGoals(event))")
    EventSimplifiedResponse toSimplifiedResponse(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "competitionId", target = "competition.id")
    @Mapping(source = "stadiumId", target = "stadium.id")
    @Mapping(source = "clubs", target = "eventClubs")
    Event toEntity(EventRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "competitionId", target = "competition.id")
    @Mapping(source = "stadiumId", target = "stadium.id")
    @Mapping(source = "clubs", target = "eventClubs")
    void updateEntity(@MappingTarget Event event, EventRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "competitionId", target = "competition.id")
    @Mapping(source = "stadiumId", target = "stadium.id")
    @Mapping(source = "clubs", target = "eventClubs")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Event event, EventPatchRequest request);

    @AfterMapping
    default void linkEventClubs(@MappingTarget Event event) {
        if (event.getEventClubs() != null) {
            event.getEventClubs().forEach(eventClub -> eventClub.setEvent(event));
        }
    }

    @Named("getHomeClubName")
    default String getHomeClubName(Event event) {
        if (event.getEventClubs() == null || event.getEventClubs().isEmpty()) return null;
        return event.getEventClubs().stream()
                .filter(EventClub::getIsHome)
                .findFirst()
                .map(ec -> ec.getClub().getName())
                .orElse(null);
    }

    @Named("getAwayClubName")
    default String getAwayClubName(Event event) {
        if (event.getEventClubs() == null || event.getEventClubs().isEmpty()) return null;
        return event.getEventClubs().stream()
                .filter(ec -> !ec.getIsHome())
                .findFirst()
                .map(ec -> ec.getClub().getName())
                .orElse(null);
    }

    @Named("getHomeGoals")
    default Integer getHomeGoals(Event event) {
        if (event.getEventClubs() == null || event.getEventClubs().isEmpty()) return 0;
        return event.getEventClubs().stream()
                .filter(EventClub::getIsHome)
                .findFirst()
                .map(ec -> ec.getGoals() != null ? ec.getGoals().size() : 0)
                .orElse(0);
    }

    @Named("getAwayGoals")
    default Integer getAwayGoals(Event event) {
        if (event.getEventClubs() == null || event.getEventClubs().isEmpty()) return 0;
        return event.getEventClubs().stream()
                .filter(ec -> !ec.getIsHome())
                .findFirst()
                .map(ec -> ec.getGoals() != null ? ec.getGoals().size() : 0)
                .orElse(0);
    }
}
