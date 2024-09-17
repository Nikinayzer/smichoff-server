package nikinayzer.smichoffserver.db.service;

import nikinayzer.smichoffserver.db.entity.Route;
import nikinayzer.smichoffserver.db.repository.AttemptRepository;
import nikinayzer.smichoffserver.db.repository.RouteRepository;
import nikinayzer.smichoffserver.db.repository.UserRepository;
import nikinayzer.smichoffserver.endpoints.dto.RouteDTO;
import nikinayzer.smichoffserver.endpoints.exceptions.route.NoRouteFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final AttemptRepository attemptRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RouteService(UserRepository userRepository, RouteRepository routeRepository, AttemptRepository attemptRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.attemptRepository = attemptRepository;
        this.modelMapper = modelMapper;
    }

    private RouteDTO convertToDTO(Route route) {
        return modelMapper.map(route, RouteDTO.class);
    }

    public List<RouteDTO> getAllRoutes() {
        return routeRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    public RouteDTO findById(long id) {
        return routeRepository.findById(id).map(this::convertToDTO).orElseThrow(() -> new NoRouteFoundException("Route not found"));
    }

    public Route findRouteByName(String name) {
        return routeRepository.findByName(name).orElseThrow(() -> new NoRouteFoundException("Route not found"));
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
