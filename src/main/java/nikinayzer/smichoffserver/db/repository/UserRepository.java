package nikinayzer.smichoffserver.db.repository;

import jakarta.persistence.LockModeType;
import nikinayzer.smichoffserver.db.entity.User;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{

    @Override
    List<User> findAll();

    Optional<User> findUserById(long id);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    <S extends User> S save(S user);

    /**
     * Deletes user, returns if successful
     * @param id id of user to delete
     */
    @Lock(LockModeType.WRITE)
    void deleteById(long id);


}

