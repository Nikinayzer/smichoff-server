package nikinayzer.smichoffserver.db.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Attempt> attempts = new ArrayList<>();

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Instant registeredAt;
    @Column(nullable = false)
    private Instant updatedAt;
    @Column(nullable = false)
    private Role role;


}
