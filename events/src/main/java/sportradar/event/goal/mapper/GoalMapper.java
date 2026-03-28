package sportradar.event.goal.mapper;

import org.mapstruct.*;
import sportradar.event.goal.dto.request.GoalPatchRequest;
import sportradar.event.goal.dto.request.GoalRequest;
import sportradar.event.goal.dto.response.GoalResponse;
import sportradar.event.goal.model.Goal;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalResponse toResponse(Goal goal);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventClub", ignore = true)
    Goal toEntity(GoalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventClub", ignore = true)
    void updateEntity(@MappingTarget Goal goal, GoalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventClub", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Goal goal, GoalPatchRequest request);
}
