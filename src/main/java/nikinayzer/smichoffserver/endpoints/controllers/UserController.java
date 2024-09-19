package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.UserService;
import nikinayzer.smichoffserver.endpoints.dto.user.NewUserDTO;
import nikinayzer.smichoffserver.endpoints.dto.user.UserListDTO;
import nikinayzer.smichoffserver.endpoints.dto.user.UserRegistrationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for user related endpoints
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user account
     *
     * @param newUserDTO - NewUserDTO object containing user details
     * @return ResponseEntity<UserRegistrationResponseDTO> - response entity containing the created user
     */
    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserRegistrationResponseDTO> register(@RequestBody NewUserDTO newUserDTO) {

        UserRegistrationResponseDTO createdUser = userService.registerNewUserAccount(newUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * Retrieve user by id
     *
     * @param id - Long id of the user
     * @return ResponseEntity<UserListDTO> - response entity containing the user
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UserListDTO> getUserById(@PathVariable("id") Long id) {
        UserListDTO userDTO = userService.findById(id);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Retrieve all users
     *
     * @return ResponseEntity<List < UserListDTO>> - response entity containing all users
     */
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<UserListDTO>> getAllUsers() {
        List<UserListDTO> usersDTO = userService.getAllUsers();
        return ResponseEntity.ok(usersDTO);
    }


}
