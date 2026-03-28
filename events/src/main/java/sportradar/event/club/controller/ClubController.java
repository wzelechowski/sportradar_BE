package sportradar.event.club.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportradar.event.club.dto.request.ClubPatchRequest;
import sportradar.event.club.dto.request.ClubRequest;
import sportradar.event.club.dto.response.ClubResponse;
import sportradar.event.club.service.ClubService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @GetMapping
    public ResponseEntity<List<ClubResponse>> getAllClubs() {
        return ResponseEntity.ok().body(clubService.getAllClubs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClubResponse> getClubById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(clubService.getClubById(id));
    }

    @PostMapping
    public ResponseEntity<ClubResponse> createClub(@Valid @RequestBody ClubRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clubService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable UUID id) {
        clubService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClubResponse> updateClub(@PathVariable UUID id, @Valid @RequestBody ClubRequest request) {
        return ResponseEntity.ok(clubService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClubResponse> patchClub(@PathVariable UUID id, @Valid @RequestBody ClubPatchRequest request) {
        return ResponseEntity.ok(clubService.patch(id, request));
    }
}
