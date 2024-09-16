package nikinayzer.smichoffserver.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.annotation.PostConstruct;
import nikinayzer.smichoffserver.db.entity.Route;
import nikinayzer.smichoffserver.db.repository.RouteRepository;
import nikinayzer.smichoffserver.endpoints.dto.RouteDTO;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader {
    private final static String ROUTES_JSON = "routes.json";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    private final RouteRepository routeRepository;

    public DataLoader(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @PostConstruct
    public void loadData() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/routes.json");

            if (inputStream == null) {
                throw new IllegalStateException("File not found");
            }

            List<RouteDTO> routeDTOs = OBJECT_MAPPER.readValue(
                    inputStream,
                    new TypeReference<>() {
                    }
            );

            for (RouteDTO routeDTO : routeDTOs) {
                Route route = convertToEntity(routeDTO);
                routeRepository.save(route);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load data", e);
        }
    }

    private Route convertToEntity(RouteDTO routeDTO) {
        Route route = new Route();
        route.setId(routeDTO.getId());
        route.setHolds(routeDTO.getHolds());
        route.setName(routeDTO.getName());
        route.setDifficulty(routeDTO.getDifficulty());
        route.setSector(routeDTO.getSector());
        route.setLineNumber(routeDTO.getLineNumber());
        route.setSetter(routeDTO.getSetter());
        route.setCreationDate(routeDTO.getCreationDate());
        route.setCharacter(routeDTO.getCharacter());
        route.setHeight(routeDTO.getHeight());
        route.setPlannedUntil(routeDTO.getPlannedUntil());
        route.setArchive(routeDTO.isArchive());
        return route;
    }
}
