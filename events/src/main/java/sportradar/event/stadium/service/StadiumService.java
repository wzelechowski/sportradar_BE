package sportradar.event.stadium.service;


import sportradar.event.stadium.dto.request.StadiumPatchRequest;
import sportradar.event.stadium.dto.request.StadiumRequest;
import sportradar.event.stadium.dto.response.StadiumResponse;

import java.util.List;
import java.util.UUID;

public interface StadiumService {
    List<StadiumResponse> getAllStadiums();

    StadiumResponse getStadiumById(UUID id);

    StadiumResponse create(StadiumRequest request);

    void delete(UUID id);

    StadiumResponse update(UUID id, StadiumRequest request);

    StadiumResponse patch(UUID id, StadiumPatchRequest request);
}
