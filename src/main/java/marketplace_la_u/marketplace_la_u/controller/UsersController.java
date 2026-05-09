package marketplace_la_u.marketplace_la_u.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.auth.RegisterRequest;
import marketplace_la_u.marketplace_la_u.DTO.user.UserResponse;
import marketplace_la_u.marketplace_la_u.DTO.user.UserUpdateRequest;
import marketplace_la_u.marketplace_la_u.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {


    private final UsersService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest user){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUser(){
        return ResponseEntity.ok(service.listUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest userDto){
       return ResponseEntity.ok(service.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> consultById(@PathVariable Long id){
        return ResponseEntity.ok((service.consultById(id)));
    }

}
