package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.UserService;
import nikinayzer.smichoffserver.endpoints.dto.NewUserDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserListDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserRegistrationResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(path = "/user/{id}", produces = "application/json")
    public ResponseEntity<UserListDTO> getUserById(@PathVariable("id") Long id) {
        UserListDTO userDTO = userService.findById(id);
        return ResponseEntity.ok(userDTO);
    }

    // Fetch all users and return as a list of UserDTOs
    @GetMapping(path = "/users/all", produces = "application/json")
    public ResponseEntity<List<UserListDTO>> getAllUsers() {
        List<UserListDTO> usersDTO = userService.getAllUsers();
        return ResponseEntity.ok(usersDTO);
    }

    // Register a new user, consuming a NewUserDTO and returning a UserDTO
    @PostMapping(path = "/users/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserRegistrationResponseDTO> register(@RequestBody NewUserDTO newUserDTO) {
//        if (userService.existsByUsername(newUserDTO.getUsername())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(null);
//        }
//        if (userService.existsByEmail(newUserDTO.getMail())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body(null);
//        }
        UserRegistrationResponseDTO createdUser = userService.registerNewUserAccount(newUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
