package marketplace_la_u.marketplace_la_u.DTO.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @Size(min = 3, message = "Minimo 2 caracteres")
    private String name;

    @Email(message = "Correo invalido.")
    private String email;
    private String profession;
    private Long careerId;
    private Long universityId;
    private String address;
}
