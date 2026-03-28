package sportradar.event.event.mapper;

import org.mapstruct.*;
import sportradar.event.competition.mapper.CompetitionMapper;
import sportradar.event.event.dto.request.EventPatchRequest;
import sportradar.event.event.dto.request.EventRequest;
import sportradar.event.event.dto.response.EventResponse;
import sportradar.event.event.model.Event;
import sportradar.event.eventClub.mapper.EventClubMapper;
import sportradar.event.stadium.mapper.StadiumMapper;

@Mapper(componentModel = "spring", uses = {EventClubMapper.class, StadiumMapper.class, CompetitionMapper.class})
public interface EventMapper {
    EventResponse toResponse(Event event);

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
}
