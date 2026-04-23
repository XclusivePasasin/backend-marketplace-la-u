package marketplace_la_u.marketplace_la_u.DTO.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "nombre obligatorio.")
    private String name;

    @NotBlank(message = "Username obligatorio")
    private String username;

    @NotBlank(message = "Email obligatorio")
    @Email(message = "correo invalido")
    private String email;

    @NotBlank(message = "Contraseña obligatorio")
    @Size(min = 6, message = "contraseña tiene que tener minimo 6 caracteres")
    private String password;

    private String profession;
}
