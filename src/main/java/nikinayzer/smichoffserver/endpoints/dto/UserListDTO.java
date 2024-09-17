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
public class UserListDTO {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Instant registeredAt;
    private Instant updatedAt;
    private String role;
}
