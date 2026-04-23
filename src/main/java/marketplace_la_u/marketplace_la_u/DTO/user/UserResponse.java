package marketplace_la_u.marketplace_la_u.DTO.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace_la_u.marketplace_la_u.model.Users;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String profession;
    private String email;

    public UserResponse(Users user){
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profession = user.getProfession();
    }
}
