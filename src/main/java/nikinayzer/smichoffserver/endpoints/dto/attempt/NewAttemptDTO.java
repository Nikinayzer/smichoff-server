package nikinayzer.smichoffserver.endpoints.dto.attempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAttemptDTO {
    private Instant date;
    private Integer strengthRating;
    private Integer enduranceRating;
    private String comment;
    private int duration;

}
