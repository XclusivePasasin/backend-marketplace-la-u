package marketplace_la_u.marketplace_la_u.service;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    public Users registrarUsuario(Users user){

        if (user.getName() == null || user.getName().isBlank()){
            throw new RuntimeException("El nombre es obligatorio.");
        }

        if (user.getEmail() == null || !user.getEmail().contains("@") || user.getEmail().isBlank()){
            throw new RuntimeException("El correo no es valido.");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6 ){
            throw new RuntimeException("La contraseña debe tener minimo 6 caracteres.");
        }

        if (user.getUniversidades() == null){
            throw new RuntimeException("Campo universidad obligatorio.");
        }

        if (user.getRole() == null){
            throw new RuntimeException("Campo role obligatorio.");
        }

        if (user.getGender() == null){
            throw new RuntimeException("Campo genero obligatorio.");
        }

        return repository.save(user);
    }

}
