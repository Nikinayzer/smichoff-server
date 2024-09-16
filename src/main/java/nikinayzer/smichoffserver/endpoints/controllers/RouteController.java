package nikinayzer.smichoffserver.endpoints.controllers;

import nikinayzer.smichoffserver.db.service.RouteService;
import nikinayzer.smichoffserver.db.service.UserService;
import nikinayzer.smichoffserver.endpoints.dto.RouteDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserListDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
