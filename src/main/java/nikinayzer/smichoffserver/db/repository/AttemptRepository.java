package nikinayzer.smichoffserver.db.repository;

import nikinayzer.smichoffserver.db.entity.Attempt;
import nikinayzer.smichoffserver.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {

    Optional<Attempt> findById(long id);

    @Query("select a from Attempt a where a.user.id = ?1")
    List<Attempt> findByUser(User user);

    <S extends Attempt> S save(S attempt);

    void deleteById(long id);
}
