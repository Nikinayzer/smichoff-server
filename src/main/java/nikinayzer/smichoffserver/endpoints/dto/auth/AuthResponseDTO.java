package nikinayzer.smichoffserver.endpoints.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token; // JWT
    private String refreshToken; // Refresh token for renewing access token
    private Long userId;
    private String username;
}