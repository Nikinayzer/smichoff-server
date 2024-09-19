package nikinayzer.smichoffserver.db.service;

import nikinayzer.smichoffserver.db.entity.User;
import nikinayzer.smichoffserver.endpoints.dto.AuthRequestDTO;
import nikinayzer.smichoffserver.endpoints.dto.AuthResponseDTO;
import nikinayzer.smichoffserver.endpoints.exceptions.auth.AuthFailedException;
import nikinayzer.smichoffserver.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service for authentication related operations
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Authenticate a user with the provided credentials
     *
     * @param request - AuthRequestDTO object containing user credentials
     * @return AuthResponseDTO - response object containing the JWT token
     */
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        try {
            // Perform authentication
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            // Retrieve the authenticated user from the authentication object
            User user = (User) auth.getPrincipal();
            // Set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwtToken = jwtService.generateToken(user);

            return AuthResponseDTO.builder()
                    .token(jwtToken)
                    .username(user.getUsername())
                    .userId(user.getId())
                    .build();

        } catch (Exception ex) {
            throw new AuthFailedException("Authentication failed: " + ex.getMessage());
        }
    }
}
