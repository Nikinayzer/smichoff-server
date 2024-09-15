package nikinayzer.smichoffserver.endpoints.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {
    private String username;
    private String mail;
    private String password;
    private String firstName;
    private String lastName;
}
