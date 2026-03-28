package sportradar.event.club.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import sportradar.event.club.dto.request.ClubPatchRequest;
import sportradar.event.club.dto.request.ClubRequest;
import sportradar.event.club.dto.response.ClubResponse;
import sportradar.event.club.model.Club;

@Mapper(componentModel = "spring")
public interface ClubMapper {
    ClubResponse toResponse(Club club);

    Club toEntity(ClubRequest request);

    void updateEntity(@MappingTarget Club club, ClubRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Club club, ClubPatchRequest request);
}
