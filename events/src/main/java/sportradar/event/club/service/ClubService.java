package sportradar.event.club.service;

import sportradar.event.club.dto.request.ClubPatchRequest;
import sportradar.event.club.dto.request.ClubRequest;
import sportradar.event.club.dto.response.ClubResponse;

import java.util.List;
import java.util.UUID;

public interface ClubService {
    List<ClubResponse> getAllClubs();

    ClubResponse getClubById(UUID id);

    ClubResponse create(ClubRequest request);

    void delete(UUID id);

    ClubResponse update(UUID id, ClubRequest request);

    ClubResponse patch(UUID id, ClubPatchRequest request);
}
