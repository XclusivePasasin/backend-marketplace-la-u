package marketplace_la_u.marketplace_la_u.config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthenticatedUserMock {
    // Este ID debe coincidir con un ID de usuario real que tengan en su tabla 'users'
    public Long getAuthenticatedUserId(){
        return 1L;
    }
}