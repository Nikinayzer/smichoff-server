package nikinayzer.smichoffserver.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "attempts")
@Getter
@Setter
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn
    private Route route;

    @Column(nullable = false)
    private Instant attemptDate;

    @Column
    private Integer strengthRating;

    @Column
    private Integer enduranceRating;

    private String comment;
}
