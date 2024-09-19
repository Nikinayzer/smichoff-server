package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.AttemptService;
import nikinayzer.smichoffserver.endpoints.dto.attempt.AttemptDTO;
import nikinayzer.smichoffserver.endpoints.dto.attempt.NewAttemptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attempts")
public class AttemptController {

    @Autowired
    private AttemptService attemptService;

    @GetMapping("/{id}") // Get attempt by ID
    public ResponseEntity<AttemptDTO> getAttemptById(@PathVariable long id) {
        return ResponseEntity.ok(attemptService.getById(id));
    }

    @GetMapping("/routes/{routeId}") // Get all attempts by route ID
    public ResponseEntity<List<AttemptDTO>> getAttemptsByRouteId(@PathVariable long routeId) {
        return ResponseEntity.ok(attemptService.getAttemptsByRouteId(routeId));
    }

    @GetMapping("/users/{userId}") // Get all attempts by user ID
    public ResponseEntity<List<AttemptDTO>> getAttemptsByUserId(@PathVariable long userId) {
        return ResponseEntity.ok(attemptService.getAllAttemptsByUserId(userId));
    }

    @GetMapping("/users/{userId}/routes/{routeId}") // Get attempts by user ID and route ID
    public ResponseEntity<List<AttemptDTO>> getAttemptsByUserIdAndRouteId(@PathVariable long userId, @PathVariable long routeId) {
        return ResponseEntity.ok(attemptService.getAllAttemptsByUserIdForRouteId(userId, routeId));
    }

    @PostMapping // Register a new attempt
    public ResponseEntity<NewAttemptDTO> registerAttempt(@RequestBody NewAttemptDTO attemptDTO, @RequestParam long userId, @RequestParam long routeId) {
        return ResponseEntity.ok(attemptService.registerAttempt(attemptDTO, userId, routeId));
    }

    @DeleteMapping("/{id}") // Delete an attempt
    public ResponseEntity<AttemptDTO> deleteAttempt(@PathVariable long id) {
        return ResponseEntity.ok(attemptService.deleteAttempt(id));
    }
}


