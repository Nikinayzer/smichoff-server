package nikinayzer.smichoffserver.endpoints.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAttemptDTO {
    private Long id;
    private Long userId;
    private String username;
    private Long routeId;
    private Instant attemptDate;
    private Integer strengthRating;
    private Integer enduranceRating;
    private String comment;
}
