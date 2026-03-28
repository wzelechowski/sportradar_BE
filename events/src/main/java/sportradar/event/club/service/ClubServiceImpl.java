package sportradar.event.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sportradar.event.club.dto.request.ClubPatchRequest;
import sportradar.event.club.dto.request.ClubRequest;
import sportradar.event.club.dto.response.ClubResponse;
import sportradar.event.club.mapper.ClubMapper;
import sportradar.event.club.model.Club;
import sportradar.event.club.repository.ClubRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final ClubMapper clubMapper;

    @Override
    public List<ClubResponse> getAllClubs() {
        return clubRepository.findAll()
                .stream()
                .map(clubMapper::toResponse)
                .toList();
    }

    @Override
    public ClubResponse getClubById(UUID id) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        return clubMapper.toResponse(club);
    }

    @Override
    @Transactional
    public ClubResponse create(ClubRequest request) {
        Club club = clubMapper.toEntity(request);
        clubRepository.save(club);
        return clubMapper.toResponse(club);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        clubRepository.delete(club);
    }

    @Override
    @Transactional
    public ClubResponse update(UUID id, ClubRequest request) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        clubMapper.updateEntity(club, request);
        clubRepository.save(club);
        return clubMapper.toResponse(club);
    }

    @Override
    @Transactional
    public ClubResponse patch(UUID id, ClubPatchRequest request) {
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Club not found"));
        clubMapper.patchEntity(club, request);
        clubRepository.save(club);
        return clubMapper.toResponse(club);
    }
}
