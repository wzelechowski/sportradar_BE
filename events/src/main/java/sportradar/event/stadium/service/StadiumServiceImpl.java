package sportradar.event.stadium.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.stadium.dto.request.StadiumPatchRequest;
import sportradar.event.stadium.dto.request.StadiumRequest;
import sportradar.event.stadium.dto.response.StadiumResponse;
import sportradar.event.stadium.mapper.StadiumMapper;
import sportradar.event.stadium.model.Stadium;
import sportradar.event.stadium.repository.StadiumRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {
    private final StadiumRepository stadiumRepository;
    private final StadiumMapper stadiumMapper;

    @Override
    public List<StadiumResponse> getAllStadiums() {
        return stadiumRepository.findAll()
                .stream()
                .map(stadiumMapper::toResponse)
                .toList();
    }

    @Override
    public StadiumResponse getStadiumById(UUID id) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stadium not found"));
        return stadiumMapper.toResponse(stadium);
    }

    @Override
    @Transactional
    public StadiumResponse create(StadiumRequest request) {
        Stadium stadium = stadiumMapper.toEntity(request);
        stadium = stadiumRepository.save(stadium);
        return stadiumMapper.toResponse(stadium);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stadium not found"));
        stadiumRepository.delete(stadium);
    }

    @Override
    @Transactional
    public StadiumResponse update(UUID id, StadiumRequest request) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stadium not found"));
        stadiumMapper.updateEntity(stadium, request);
        stadium = stadiumRepository.save(stadium);
        return stadiumMapper.toResponse(stadium);
    }

    @Override
    @Transactional
    public StadiumResponse patch(UUID id, StadiumPatchRequest request) {
        Stadium stadium = stadiumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stadium not found"));
        stadiumMapper.patchEntity(stadium, request);
        stadium = stadiumRepository.save(stadium);
        return stadiumMapper.toResponse(stadium);
    }
}
