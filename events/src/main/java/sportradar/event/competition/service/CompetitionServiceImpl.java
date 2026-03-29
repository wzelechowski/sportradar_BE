package sportradar.event.competition.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.competition.dto.request.CompetitionPatchRequest;
import sportradar.event.competition.dto.request.CompetitionRequest;
import sportradar.event.competition.dto.response.CompetitionResponse;
import sportradar.event.competition.mapper.CompetitionMapper;
import sportradar.event.competition.model.Competition;
import sportradar.event.competition.repository.CompetitionRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    @Override
    public List<CompetitionResponse> getAllCompetitions() {
        return competitionRepository.findAll()
                .stream()
                .map(competitionMapper::toResponse)
                .toList();
    }

    @Override
    public CompetitionResponse getCompetitionById(UUID id) {
        Competition competition = competitionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
        return competitionMapper.toResponse(competition);
    }

    @Override
    @Transactional
    public CompetitionResponse create(CompetitionRequest request) {
        Competition competition = competitionMapper.toEntity(request);
        competition = competitionRepository.save(competition);
        return competitionMapper.toResponse(competition);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Competition competition = competitionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
        competitionRepository.delete(competition);
    }

    @Override
    @Transactional
    public CompetitionResponse update(UUID id, CompetitionRequest request) {
        Competition competition = competitionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
        competitionMapper.updateEntity(competition, request);
        competition = competitionRepository.save(competition);
        return competitionMapper.toResponse(competition);
    }

    @Override
    @Transactional
    public CompetitionResponse patch(UUID id, CompetitionPatchRequest request) {
        Competition competition = competitionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
        competitionMapper.patchEntity(competition, request);
        competition = competitionRepository.save(competition);
        return competitionMapper.toResponse(competition);    }
}
