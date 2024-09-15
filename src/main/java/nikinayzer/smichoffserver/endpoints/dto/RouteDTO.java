package nikinayzer.smichoffserver.endpoints.dto;

import java.time.Instant;

public record RouteDTO(
        Long id,
        String holds,
        String name,
        String difficulty,
        String sector,
        String lineNumber,
        String setter,
        Instant creationDate,
        String character,
        String height,
        Instant plannedUntil,
        boolean archive
) {
}
