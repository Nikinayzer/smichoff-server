package nikinayzer.smichoffserver.db.service;

import nikinayzer.smichoffserver.db.entity.Attempt;
import nikinayzer.smichoffserver.db.entity.User;
import nikinayzer.smichoffserver.db.repository.AttemptRepository;
import nikinayzer.smichoffserver.db.repository.RouteRepository;
import nikinayzer.smichoffserver.db.repository.UserRepository;
import nikinayzer.smichoffserver.endpoints.dto.NewUserDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserListDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserRegistrationResponseDTO;
import nikinayzer.smichoffserver.endpoints.exceptions.EmailAlreadyExistsException;
import nikinayzer.smichoffserver.endpoints.exceptions.UsernameAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static nikinayzer.smichoffserver.db.entity.Role.USER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final AttemptRepository attemptRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RouteRepository routeRepository, AttemptRepository attemptRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.attemptRepository = attemptRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    // Converts User entity to UserDTO (no ResponseEntity here)
    private UserListDTO convertToDTO(User user) {
        return modelMapper.map(user, UserListDTO.class);
    }

    public List<UserListDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserListDTO findById(long id) {
        return userRepository.findUserById(id).map(this::convertToDTO).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserListDTO findByUsername(String username) {
        return userRepository.findUserByUsername(username).stream().map(this::convertToDTO).findFirst().orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserRegistrationResponseDTO registerNewUserAccount(NewUserDTO newUserDTO) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (existsByEmail(newUserDTO.getMail())) {
            throw new EmailAlreadyExistsException(
                    "There is an account with that email address:" + newUserDTO.getMail());
        }
        if (existsByUsername(newUserDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    "There is an account with that username:" + newUserDTO.getUsername());
        }
        User user = new User();
        user.setUsername(newUserDTO.getUsername());
        user.setEmail(newUserDTO.getMail());
        user.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        user.setFirstName(newUserDTO.getFirstName());
        user.setLastName(newUserDTO.getLastName());
        user.setRole(USER);

        user.setRegisteredAt(Instant.now());
        user.setUpdatedAt(Instant.now());

        saveUser(user);
        return modelMapper.map(user, UserRegistrationResponseDTO.class);

    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean userExists(User user) {
        return userRepository.existsByUsername(user.getUsername()) && userRepository.existsByEmail(user.getEmail());
    }

    public List<Attempt> getAttempts(User user) {
        return attemptRepository.findByUser(user);
    }
}
