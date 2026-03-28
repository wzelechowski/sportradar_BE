package sportradar.event.club.mapper;

import org.mapstruct.*;
import sportradar.event.club.dto.request.ClubPatchRequest;
import sportradar.event.club.dto.request.ClubRequest;
import sportradar.event.club.dto.response.ClubResponse;
import sportradar.event.club.model.Club;

@Mapper(componentModel = "spring")
public interface ClubMapper {
    ClubResponse toResponse(Club club);

    @Mapping(target = "id", ignore = true)
    Club toEntity(ClubRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Club club, ClubRequest request);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Club club, ClubPatchRequest request);
}
