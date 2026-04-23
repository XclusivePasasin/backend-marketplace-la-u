package marketplace_la_u.marketplace_la_u.controller;
import jakarta.validation.Valid;
import marketplace_la_u.marketplace_la_u.DTO.auth.RegisterRequest;
import marketplace_la_u.marketplace_la_u.DTO.user.UserResponse;
import marketplace_la_u.marketplace_la_u.DTO.user.UserUpdateRequest;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest user){

        try {
            UserResponse newUser = service.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (RuntimeException error){
            return ResponseEntity.badRequest().body(error.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUser(){
        return ResponseEntity.ok(service.listUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequest userDto, @PathVariable Long id){
        try{
            UserResponse userUpdate = service.updateUser(id, userDto);
            return ResponseEntity.ok(userUpdate);
        } catch (RuntimeException error){
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try{
            service.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException error){
            return ResponseEntity.badRequest().body(error.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> consultById(@PathVariable Long id){
        return ResponseEntity.ok((service.consultById(id)));
    }

}
