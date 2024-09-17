package nikinayzer.smichoffserver.endpoints.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
    private int status;
    private String message;
    private String description;
    private Instant timestamp;

}
