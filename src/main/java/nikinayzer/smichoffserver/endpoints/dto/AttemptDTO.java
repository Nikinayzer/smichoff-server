package nikinayzer.smichoffserver.endpoints.dto;

import java.time.Instant;

public record AttemptDTO(
        Long id,
        Long userId,
        String username,
        Long routeId,
        String routeName,
        Instant attemptDate,
        Integer strengthRating,
        Integer enduranceRating,
        String comment
) {
}
