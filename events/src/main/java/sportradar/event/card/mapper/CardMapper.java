package sportradar.event.card.mapper;


import org.mapstruct.*;
import sportradar.event.card.dto.request.CardPatchRequest;
import sportradar.event.card.dto.request.CardRequest;
import sportradar.event.card.dto.response.CardResponse;
import sportradar.event.card.model.Card;


@Mapper(componentModel = "spring")
public interface CardMapper {
    CardResponse toResponse(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventClub", ignore = true)
    Card toEntity(CardRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventClub", ignore = true)
    void updateEntity(@MappingTarget Card card, CardRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventClub", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntity(@MappingTarget Card card, CardPatchRequest request);
}
