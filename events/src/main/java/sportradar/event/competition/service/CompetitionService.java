package sportradar.event.competition.service;

import sportradar.event.competition.dto.request.CompetitionPatchRequest;
import sportradar.event.competition.dto.request.CompetitionRequest;
import sportradar.event.competition.dto.response.CompetitionResponse;

import java.util.List;
import java.util.UUID;

public interface CompetitionService {

    List<CompetitionResponse> getAllCompetitions();

    CompetitionResponse getCompetitionById(UUID id);

    CompetitionResponse create(CompetitionRequest request);

    void delete(UUID id);

    CompetitionResponse update(UUID id, CompetitionRequest request);

    CompetitionResponse patch(UUID id, CompetitionPatchRequest request);
}
