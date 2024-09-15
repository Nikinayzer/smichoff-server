package nikinayzer.smichoffserver.db.repository;

import nikinayzer.smichoffserver.db.entity.Route;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends CrudRepository<Route, Long> {

    long count();

    @Override
    List<Route> findAll();

    @Query("SELECT r FROM Route r WHERE r.sector = ?1")
    List<Route> findBySector(String sector);

    Optional<Route> findById(long id);
    Optional<Route> findByName(String name);



    void deleteById(long id);


    boolean existsByName(String name);
}
