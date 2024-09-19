package nikinayzer.smichoffserver.endpoints.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nikinayzer.smichoffserver.endpoints.controllers.UserController;

import java.time.Instant;
import java.util.List;

/**
 * DTO for user list, used for returning user details in {@link UserController#getAllUsers()} and {@link UserController#getUserById(Long)}
 * <p>
 * This DTO is used to return user details, containing only the necessary details, without any sensitive information.
 */
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
    private List<String> roles;
}
