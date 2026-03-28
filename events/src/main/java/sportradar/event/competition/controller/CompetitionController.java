package sportradar.event.competition.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportradar.event.competition.dto.request.CompetitionPatchRequest;
import sportradar.event.competition.dto.request.CompetitionRequest;
import sportradar.event.competition.dto.response.CompetitionResponse;
import sportradar.event.competition.service.CompetitionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/competitions")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<List<CompetitionResponse>> getAllCompetitions() {
        return ResponseEntity.ok(competitionService.getAllCompetitions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionResponse> getCompetitionById(@PathVariable UUID id ){
        return ResponseEntity.ok(competitionService.getCompetitionById(id));
    }

    @PostMapping
    public ResponseEntity<CompetitionResponse> createCompetition(@Valid @RequestBody CompetitionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competitionService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompetitionResponse> deleteCompetition(@PathVariable UUID id) {
        competitionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetitionResponse> updateCompetition(@PathVariable UUID id, @Valid @RequestBody CompetitionRequest request) {
        return ResponseEntity.ok(competitionService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompetitionResponse> patchCompetition(@PathVariable UUID id, @Valid @RequestBody CompetitionPatchRequest request) {
        return ResponseEntity.ok(competitionService.patch(id, request));
    }
}
