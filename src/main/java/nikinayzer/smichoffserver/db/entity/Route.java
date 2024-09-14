package nikinayzer.smichoffserver.db.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "routes")
@Getter
@Setter
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String holds;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String difficulty;
    @Column(nullable = false)
    private String sector;
    @Column
    private String lineNumber;
    @Column
    private String setter;
    @Column(nullable = false)
    private Instant creationDate;
    @Column
    private String character;
    @Column
    private String height;
    @Column
    private Instant plannedUntil;
    @Column(nullable = false)
    private boolean archive;
    @OneToMany(mappedBy = "route")
    private List<Attempt> attempts = new ArrayList<>();

}
