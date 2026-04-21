package marketplace_la_u.marketplace_la_u.controller;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Validated @RequestBody Users user){

        try {
            Users newUser = service.registrarUsuario(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException error){
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<Users>> listarUsuario(){
        return ResponseEntity.ok(service.listarUsuario());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@Validated @RequestBody Users user, @PathVariable Long id){
        try{
            Users userUpdate = service.actualizarUsuario(id, user);
            return ResponseEntity.ok(userUpdate);
        } catch (RuntimeException error){
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@Validated @PathVariable Long id){
        try{
            service.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException error){
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> consultarPorId(@PathVariable Long id){
        return ResponseEntity.ok((service.consultarPorId(id)));
    }

}
