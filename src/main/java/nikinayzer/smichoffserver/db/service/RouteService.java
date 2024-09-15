package nikinayzer.smichoffserver.db.service;

import nikinayzer.smichoffserver.db.entity.Route;
import nikinayzer.smichoffserver.db.repository.AttemptRepository;
import nikinayzer.smichoffserver.db.repository.RouteRepository;
import nikinayzer.smichoffserver.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final AttemptRepository attemptRepository;

    @Autowired
    public RouteService(UserRepository userRepository, RouteRepository routeRepository, AttemptRepository attemptRepository) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.attemptRepository = attemptRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
    public Route loadRouteById(long id) {
        return routeRepository.findById(id).orElseThrow(() -> new RuntimeException("Route not found"));
    }
    public Route loadRouteByName(String name) {
        return routeRepository.findByName(name).orElseThrow(() -> new RuntimeException("Route not found"));
    }
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }
    public void deleteRoute(long id) {
        routeRepository.deleteById(id);
    }
    public boolean existsRouteByName(String name) {
        return routeRepository.existsByName(name);
    }
    public long countRoutes() {
        return routeRepository.count();
    }
    public List<Route> getRoutesBySector(String sector) {
        return routeRepository.findBySector(sector);
    }
}
