package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.RouteService;
import nikinayzer.smichoffserver.endpoints.dto.route.RouteDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private ModelMapper modelMapper;


    // Fetch all routes and return as a list of RouteDTO
    @GetMapping(path = "/routes/all", produces = "application/json")
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        List<RouteDTO> routesDTOs = routeService.getAllRoutes();
        return ResponseEntity.ok(routesDTOs);
    }

    // Fetch a route by its ID
    @GetMapping(path = "/route/{id}", produces = "application/json")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable("id") Long id) {
        RouteDTO routeDTO = routeService.findById(id);
        return ResponseEntity.ok(routeDTO);
    }
}
