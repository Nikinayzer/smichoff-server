package nikinayzer.smichoffserver.endpoints.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    private Long id;
    private String holds;
    private String name;
    private String difficulty;
    private String sector;

    @JsonProperty("line_number")
    private String lineNumber;

    private String setter;

    @JsonProperty("creation_date")
    private LocalDate creationDate;

    private String character;
    private String height;

    @JsonProperty("planned_until")
    private LocalDate plannedUntil;

    private boolean archive;
}
