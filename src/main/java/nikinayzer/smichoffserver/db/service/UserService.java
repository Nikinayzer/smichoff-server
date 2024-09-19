package nikinayzer.smichoffserver.db.service;

import nikinayzer.smichoffserver.db.entity.User;
import nikinayzer.smichoffserver.db.repository.UserRepository;
import nikinayzer.smichoffserver.endpoints.dto.user.NewUserDTO;
import nikinayzer.smichoffserver.endpoints.dto.user.UserListDTO;
import nikinayzer.smichoffserver.endpoints.dto.user.UserRegistrationResponseDTO;
import nikinayzer.smichoffserver.endpoints.exceptions.user.EmailAlreadyExistsException;
import nikinayzer.smichoffserver.endpoints.exceptions.user.NoUserExistsException;
import nikinayzer.smichoffserver.endpoints.exceptions.user.UsernameAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for User entity, contains methods for user operations
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    private UserListDTO convertToDTO(User user) {
        return modelMapper.map(user, UserListDTO.class);
    }

    public List<UserListDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public UserListDTO findById(long id) {
        return userRepository.findUserById(id).map(this::convertToDTO).orElseThrow(() -> new NoUserExistsException("User not found"));
    }

    public UserListDTO findByUsername(String username) {
        return userRepository.findUserByUsername(username).stream().map(this::convertToDTO).findFirst().orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public UserRegistrationResponseDTO registerNewUserAccount(NewUserDTO newUserDTO) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (existsByEmail(newUserDTO.getMail())) {
            throw new EmailAlreadyExistsException("There is an account with that email address:" + newUserDTO.getMail());
        }
        if (existsByUsername(newUserDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("There is an account with that username:" + newUserDTO.getUsername());
        }
        User user = User.builder().username(newUserDTO.getUsername()).email(newUserDTO.getMail()).password(passwordEncoder.encode(newUserDTO.getPassword())).firstName(newUserDTO.getFirstName()).lastName(newUserDTO.getLastName()).roles(List.of("ROLE_USER")).isAccountNonExpired(true).isAccountNonLocked(true).isCredentialsNonExpired(true).isEnabled(true).registeredAt(Instant.now()).updatedAt(Instant.now()).build();

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

    public void validateUserExists(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NoUserExistsException("User not found"));
    }

}
