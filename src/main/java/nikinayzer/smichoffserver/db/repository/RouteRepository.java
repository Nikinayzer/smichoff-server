package nikinayzer.smichoffserver.db.repository;

import io.micrometer.common.lang.NonNullApi;
import nikinayzer.smichoffserver.db.entity.Route;
import nikinayzer.smichoffserver.db.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends CrudRepository<Route, Long> {

    long count();

    List<Route> findAll();

    @Query("SELECT r FROM Route r WHERE r.sector = ?1")
    List<Route> findBySector(String sector);

    Optional<Route> findById(long id);

    Optional<Route> findByName(String name);

    <S extends Route> S save(S route);


    void deleteById(long id);


    boolean existsByName(String name);
}
