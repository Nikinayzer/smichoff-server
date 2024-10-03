package nikinayzer.smichoffserver.db.service;

import nikinayzer.smichoffserver.db.entity.Attempt;
import nikinayzer.smichoffserver.db.entity.Route;
import nikinayzer.smichoffserver.db.entity.User;
import nikinayzer.smichoffserver.db.repository.AttemptRepository;
import nikinayzer.smichoffserver.db.repository.RouteRepository;
import nikinayzer.smichoffserver.endpoints.dto.attempt.AttemptDTO;
import nikinayzer.smichoffserver.endpoints.dto.attempt.NewAttemptDTO;
import nikinayzer.smichoffserver.endpoints.exceptions.attempt.NoAttemptFoundException;
import nikinayzer.smichoffserver.endpoints.exceptions.route.NoRouteFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final ModelMapper modelMapper;
    private final RouteRepository routeRepository;
    private final UserService userService;
    private final RouteService routeService;

    public AttemptService(AttemptRepository attemptRepository, ModelMapper modelMapper, RouteRepository routeRepository, UserService userService, RouteService routeService) {
        this.attemptRepository = attemptRepository;
        this.modelMapper = modelMapper;
        this.routeRepository = routeRepository;
        this.userService = userService;
        this.routeService = routeService;
    }

    private AttemptDTO convertToDTO(Attempt attempt) {
        return modelMapper.map(attempt, AttemptDTO.class);
    }

    public AttemptDTO getById(long id) {
        return attemptRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new NoAttemptFoundException("Attempt not found"));
    }

    public void saveAttempt(Attempt attempt) {
        attemptRepository.save(attempt);
    }

    // Fetch all attempts by user ID, throwing exception if the user does not exist
    public List<AttemptDTO> getAllAttemptsByUserId(long userId) {
        userService.validateUserExists(userId);
        return attemptRepository.findAllByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Fetch all attempts by a specific user for a specific route, ensuring both user and route exist
    public List<AttemptDTO> getAllAttemptsByUserIdForRouteId(long userId, long routeId) {
        userService.validateUserExists(userId);
        validateRouteExists(routeId);
        return attemptRepository.findAllByUserIdForRouteId(userId, routeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Fetch all attempts by route ID, throwing an exception if no route or attempt is found
    public List<AttemptDTO> getAttemptsByRouteId(long routeId) {
        validateRouteExists(routeId);
        List<AttemptDTO> attempts = attemptRepository.findAllByRouteId(routeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        if (attempts.isEmpty()) {
            throw new NoAttemptFoundException("No attempts found for the route");
        }
        return attempts;
    }

    public NewAttemptDTO registerAttempt(NewAttemptDTO newAttemptDTO, long userId, long routeId) {
        Attempt attempt = new Attempt();
        User user = modelMapper.map(userService.findById(userId), User.class);
        attempt.setUser(user);
        Route route = modelMapper.map(routeService.findById(routeId), Route.class);

        attempt.setRoute(route);
        attempt.setDate(Instant.now());
        attempt.setStrengthRating(newAttemptDTO.getStrengthRating());
        attempt.setEnduranceRating(newAttemptDTO.getEnduranceRating());
        attempt.setComment(newAttemptDTO.getComment());
        attempt.setDuration(newAttemptDTO.getDuration());

        saveAttempt(attempt);
        return modelMapper.map(attemptRepository.save(attempt), NewAttemptDTO.class);

    }

    public AttemptDTO deleteAttempt(long id) {
        AttemptDTO attemptDTO = getById(id);
        try {
            attemptRepository.deleteById(id);
        } catch (Exception e) {
            throw new NoAttemptFoundException("Attempt not found");
        }
        return attemptDTO; 
    }

    private void validateRouteExists(long routeId) {
        routeRepository.findById(routeId)
                .orElseThrow(() -> new NoRouteFoundException("Route not found"));
    }
}
