package marketplace_la_u.marketplace_la_u.service;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordHash;

    public Users registrarUsuario(Users user){

        if (repository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Correo ya registrado.");
        }

        if (repository.existsByUsername(user.getUsername())){
            throw new RuntimeException(("Username ya registrado"));
        }
        user.setPassword(passwordHash.encode(user.getPassword()));

        return repository.save(user);
    }

    public List<Users> listarUsuario(){
        return repository.findAll();
    }

    public void eliminarUsuario(Long id){
        if (!repository.existsById(id)){
            throw new RuntimeException("Usuario no encontrado");
        }
        repository.deleteById(id);
    }

    public Users actualizarUsuario(Long id, Users nuevosDatos){
        return repository.findById(id).map(user ->{
            user.setName(nuevosDatos.getName());
            user.setAddress(nuevosDatos.getAddress());
            user.setCarreras(nuevosDatos.getCarreras());
            user.setUniversidades(nuevosDatos.getUniversidades());
            return repository.save(user);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Users consultarPorId(Long id){
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
    }
}
