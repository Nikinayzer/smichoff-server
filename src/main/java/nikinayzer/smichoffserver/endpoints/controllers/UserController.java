package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.UserService;
import nikinayzer.smichoffserver.endpoints.dto.NewUserDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserRegistrationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/welcome", produces = "application/json")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to Smichoff Server!");
    }

    // Fetch all users and return as a list of UserDTOs
    @GetMapping(path = "/users/all", produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usersDTO = userService.getAllUsers();
        return ResponseEntity.ok(usersDTO);
    }

    // Register a new user, consuming a NewUserDTO and returning a UserDTO
    @PostMapping(path = "/users/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserRegistrationResponseDTO> register(@RequestBody NewUserDTO newUserDTO) {
        // Check for existing username or email
        if (userService.existsByUsername(newUserDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        if (userService.existsByEmail(newUserDTO.getMail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        UserRegistrationResponseDTO createdUser = userService.registerNewUserAccount(newUserDTO);

        // Return 201 Created status with UserDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
