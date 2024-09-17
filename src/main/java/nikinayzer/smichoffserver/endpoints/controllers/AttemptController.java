package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.AttemptService;
import nikinayzer.smichoffserver.endpoints.dto.AttemptDTO;
import nikinayzer.smichoffserver.endpoints.dto.NewAttemptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttemptController {

    @Autowired
    private AttemptService attemptService;

    @GetMapping(path = "/attempts/{id}", produces = "application/json")
    public ResponseEntity<AttemptDTO> getAttemptById(@PathVariable long id) {
        return ResponseEntity.ok(attemptService.getById(id));
    }
    @GetMapping(path = "/route/{id}/attempts", produces = "application/json")
    public ResponseEntity<List<AttemptDTO>> getAllAttemptsByRouteId(@PathVariable long id) {
        return ResponseEntity.ok(attemptService.getAttemptsByRouteId(id));
    }

    @GetMapping(path = "/user/{userId}/attempts", produces = "application/json")
    public ResponseEntity<List<AttemptDTO>> getAllAttemptsByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(attemptService.getAllAttemptsByUserId(userId));
    }
    @GetMapping(path = "/user/{userId}/route/{routeId}/attempts", produces = "application/json")
    public ResponseEntity<List<AttemptDTO>> getAllAttemptsByUserIdAndRouteId(@PathVariable long userId, @PathVariable long routeId) {
        return ResponseEntity.ok(attemptService.getAllAttemptsByUserIdForRouteId(userId, routeId));
    }

    @PostMapping(path = "/user/{userId}/route/{routeId}/attempt", consumes = "application/json", produces = "application/json")
    public ResponseEntity<NewAttemptDTO> registerAttempt(@RequestBody NewAttemptDTO attemptDTO, @PathVariable Long userId, @PathVariable Long routeId) {
        return ResponseEntity.ok(attemptService.registerAttempt(attemptDTO, userId, routeId));
    }

    @PostMapping(path = "/attempts/{id}/delete", produces = "application/json")
    public ResponseEntity<AttemptDTO> deleteAttempt(@PathVariable long id) {
        return ResponseEntity.ok(attemptService.deleteAttempt(id));
    }
}

