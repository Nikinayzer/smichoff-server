package nikinayzer.smichoffserver.controllers;

import nikinayzer.smichoffserver.db.service.UserService;
import nikinayzer.smichoffserver.endpoints.dto.NewUserDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserListDTO;
import nikinayzer.smichoffserver.endpoints.dto.UserRegistrationResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
//todo test is broken, probably because of the required authentication
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllUsers_ShouldReturnUsers() throws Exception {
        List<UserListDTO> users = Arrays.asList(
                UserListDTO.builder()
                        .id(1L)
                        .username("foo")
                        .email("foo@example.com")
                        .firstName("Foo")
                        .lastName("Foovski")
                        .registeredAt(Instant.now())
                        .updatedAt(Instant.now())
                        .roles(List.of("ROLE_USER"))
                        .build(),
                UserListDTO.builder()
                        .id(2L)
                        .username("bar")
                        .email("bar@example.com")
                        .firstName("Bar")
                        .lastName("Barov")
                        .registeredAt(Instant.now())
                        .updatedAt(Instant.now())
                        .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
                        .build()
        );

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("foo")))
                .andExpect(jsonPath("$[1].email", is("bar@example.com")));
    }

    @Test
    public void testGetAllUsers_ShouldReturnEmptyList() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testRegister_ShouldCreateUser() throws Exception {
        NewUserDTO newUserDTO = NewUserDTO.builder()
                .username("newuser")
                .password("password")
                .mail("newuser@example.com")
                .firstName("New")
                .lastName("User")
                .build();
        UserRegistrationResponseDTO responseDTO = new UserRegistrationResponseDTO("newuser", "new@example.com");

        Mockito.when(userService.registerNewUserAccount(Mockito.any(NewUserDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("newuser")))
                .andExpect(jsonPath("$.email", is("new@example.com")));
    }

    @Test
    public void testRegister_ShouldReturnConflict_WhenUsernameExists() throws Exception {
        NewUserDTO newUserDTO = NewUserDTO.builder()
                .username("foo")
                .password("password")
                .mail("fooUNQIUE@example.com")
                .firstName("Foo")
                .lastName("Foovski")
                .build();

        Mockito.when(userService.existsByUsername("foo")).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testRegister_ShouldReturnConflict_WhenEmailExists() throws Exception {
        NewUserDTO newUserDTO = NewUserDTO.builder()
                .username("barUNIQUE")
                .password("password")
                .mail("bar@example.com")
                .firstName("Bar")
                .lastName("Barov")
                .build();

        Mockito.when(userService.existsByEmail("bar@example.com")).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDTO)))
                .andExpect(status().isConflict());
    }
}
