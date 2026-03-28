package sportradar.event.eventClub.mapper;

import org.mapstruct.*;
import sportradar.event.card.mapper.CardMapper;
import sportradar.event.club.mapper.ClubMapper;
import sportradar.event.eventClub.dto.request.EventClubPatchRequest;
import sportradar.event.eventClub.dto.request.EventClubRequest;
import sportradar.event.eventClub.dto.response.EventClubResponse;
import sportradar.event.eventClub.model.EventClub;
import sportradar.event.goal.mapper.GoalMapper;

@Mapper(componentModel = "spring", uses = {CardMapper.class, GoalMapper.class, ClubMapper.class})
public interface EventClubMapper {
    EventClubResponse toResponse(EventClub eventClub);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(source = "clubId", target = "club.id")
    EventClub toEntity(EventClubRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(source = "clubId", target = "club.id")
    void updateEntity(@MappingTarget EventClub eventClub, EventClubRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(source = "clubId", target = "club.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget EventClub eventClub, EventClubPatchRequest request);

    @AfterMapping
    default void link(@MappingTarget EventClub eventClub) {
        if (eventClub.getCards() != null) {
            eventClub.getCards().forEach(card -> card.setEventClub(eventClub));
        }

        if (eventClub.getGoals() != null) {
            eventClub.getGoals().forEach(goal -> goal.setEventClub(eventClub));
        }
    }
}
