package nikinayzer.smichoffserver.endpoints.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import nikinayzer.smichoffserver.db.service.AuthService;
import nikinayzer.smichoffserver.endpoints.dto.auth.AuthRequestDTO;
import nikinayzer.smichoffserver.endpoints.dto.auth.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication related endpoints
 */
@RestController
public class AuthController {

    private final AuthService authService;
    private final long jwtExpiration;

    @Autowired
    public AuthController(AuthService authService, @Value("${jwt.expiration}") long jwtExpiration) {
        this.authService = authService;
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * Authenticate a user
     *
     * @param request  - AuthRequestDTO object containing user credentials
     * @param response - HttpServletResponse object to set JWT cookie
     * @return ResponseEntity<AuthResponseDTO> - response entity containing response {@link AuthResponseDTO}
     */
    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO request, HttpServletResponse response) {
        AuthResponseDTO authResponse = authService.authenticate(request);

        Cookie jwtCookie = new Cookie("jwt", authResponse.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge((int) jwtExpiration);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(authResponse);
    }
}
