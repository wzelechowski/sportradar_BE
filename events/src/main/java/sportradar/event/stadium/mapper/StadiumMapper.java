package sportradar.event.stadium.mapper;

import org.mapstruct.*;
import sportradar.event.stadium.dto.request.StadiumPatchRequest;
import sportradar.event.stadium.dto.request.StadiumRequest;
import sportradar.event.stadium.dto.response.StadiumResponse;
import sportradar.event.stadium.model.Stadium;

@Mapper(componentModel = "spring")
public interface StadiumMapper {
    StadiumResponse toResponse(Stadium stadium);

    @Mapping(target = "id", ignore = true)
    Stadium toEntity(StadiumRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Stadium stadium, StadiumRequest request);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Stadium stadium, StadiumPatchRequest request);
}
