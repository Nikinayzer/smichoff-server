package nikinayzer.smichoffserver.endpoints.dto;

import java.time.Instant;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttemptDTO {

    private Long id;
    private Long userId;
    private String username;
    private Long routeId;
    private String routeName;
    private Instant date;
    private Integer strengthRating;
    private Integer enduranceRating;
    private String comment;
}