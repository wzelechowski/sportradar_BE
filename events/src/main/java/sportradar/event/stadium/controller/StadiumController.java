package sportradar.event.stadium.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sportradar.event.stadium.dto.request.StadiumPatchRequest;
import sportradar.event.stadium.dto.request.StadiumRequest;
import sportradar.event.stadium.dto.response.StadiumResponse;
import sportradar.event.stadium.service.StadiumService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stadiums")
@RequiredArgsConstructor
public class StadiumController {
    private final StadiumService stadiumService;

    @GetMapping
    public ResponseEntity<List<StadiumResponse>> getAllStadiums() {
        return ResponseEntity.ok(stadiumService.getAllStadiums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StadiumResponse> getStadiumById(@PathVariable UUID id) {
        return ResponseEntity.ok(stadiumService.getStadiumById(id));
    }

    @PostMapping
    public ResponseEntity<StadiumResponse> createStadium(@Valid @RequestBody StadiumRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stadiumService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable UUID id) {
        stadiumService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StadiumResponse> updateStadium(@PathVariable UUID id, @Valid @RequestBody StadiumRequest request) {
        return ResponseEntity.ok(stadiumService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StadiumResponse> patchStadium(@PathVariable UUID id, @Valid @RequestBody StadiumPatchRequest request) {
        return ResponseEntity.ok(stadiumService.patch(id, request));
    }
}
