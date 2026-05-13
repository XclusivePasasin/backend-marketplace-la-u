package marketplace_la_u.marketplace_la_u.controller;

import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.auth.AuthResponse;
import marketplace_la_u.marketplace_la_u.DTO.auth.LoginRequest;
import marketplace_la_u.marketplace_la_u.model.Users;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import marketplace_la_u.marketplace_la_u.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        // 1. Spring Security autentica las credenciales (Si está mal, lanza error automático 403)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Si pasó la autenticación, buscamos al usuario en la Base de Datos
        Users user = usersRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Generamos el Token JWT usando la clase que creamos antes
        String token = jwtService.generateToken(user);

        // 4. Devolvemos el DTO de respuesta con el Token al frontend
        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), user.getEmail()));
    }
}