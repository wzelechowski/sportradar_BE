package sportradar.event.competition.mapper;

import org.mapstruct.*;
import sportradar.event.competition.dto.request.CompetitionPatchRequest;
import sportradar.event.competition.dto.request.CompetitionRequest;
import sportradar.event.competition.dto.response.CompetitionResponse;
import sportradar.event.competition.model.Competition;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    CompetitionResponse toResponse(Competition competition);

    @Mapping(target = "id", ignore = true)
    Competition toEntity(CompetitionRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Competition competition, CompetitionRequest request);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Competition competition, CompetitionPatchRequest request);
}
