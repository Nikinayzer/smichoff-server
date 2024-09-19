package nikinayzer.smichoffserver.db.repository;

import jakarta.persistence.LockModeType;
import nikinayzer.smichoffserver.db.entity.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    @NonNull
    List<User> findAll(); // Non-null bcs app must initialize at least one user

    Optional<User> findUserById(long id);

    Optional<User> findUserByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @NonNull
    <S extends User> S save(@NonNull S user);

    @Lock(LockModeType.WRITE)
    void deleteById(long id);


}

