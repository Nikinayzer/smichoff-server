package nikinayzer.smichoffserver.security;

import nikinayzer.smichoffserver.db.entity.User;
import nikinayzer.smichoffserver.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class DefaultUserInitializer {

    private final UserService userService;
    private final PasswordEncoder BcryptPasswordEncoder;

    @Value("${smichoff.default.user.username}")
    private String userUsername;

    @Value("${smichoff.default.user.password}")
    private String userPassword;

    @Value("${smichoff.default.admin.username}")
    private String adminUsername;

    @Value("${smichoff.default.admin.password}")
    private String adminPassword;

    @Autowired
    public DefaultUserInitializer(UserService userService, PasswordEncoder BcryptPasswordEncoder) {
        this.userService = userService;
        this.BcryptPasswordEncoder = BcryptPasswordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDefaultUser() {
        if (!userService.existsByUsername(userUsername)) {
            User user = new User();
            user.setUsername(userUsername);
            user.setEmail("user@smichoff-server.cz");
            user.setFirstName("User");
            user.setLastName("Userov");
            user.setPassword(BcryptPasswordEncoder.encode(userPassword));
            user.setRoles(List.of("ROLE_USER"));
            user.setRegisteredAt(Instant.now());
            user.setUpdatedAt(Instant.now());
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            userService.saveUser(user);
        }

        if (!userService.existsByUsername(adminUsername)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail("admin@smichoff-server.cz");
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setPassword(BcryptPasswordEncoder.encode(adminPassword));
            admin.setRoles(List.of("ROLE_ADMIN"));
            admin.setRegisteredAt(Instant.now());
            admin.setUpdatedAt(Instant.now());
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            admin.setEnabled(true);
            userService.saveUser(admin);
        }
    }
}
