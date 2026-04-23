package marketplace_la_u.marketplace_la_u.DTO.auth;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "campo obligatorio")
    private String username;

    @NotBlank(message = "campo obligatorio")
    private String password;
}
