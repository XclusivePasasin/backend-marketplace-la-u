package marketplace_la_u.marketplace_la_u.Error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    // 1. Cuando la contraseña o el usuario están mal, devolvemos 401
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleBadCredentials(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 401);
        response.put("message", "Usuario o contraseña incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 2. Si es cualquier otro error, OBLIGAMOS a la consola a pintarlo en rojo
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex) {
        ex.printStackTrace(); // ¡LA LÍNEA MÁGICA PARA QUE INTELLIJ HABLE!
        Map<String, Object> response = new HashMap<>();
        response.put("status", 500);
        response.put("message", "Error interno: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Atrapa el error de "Stock insuficiente" y lo manda limpio al frontend
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("message", ex.getMessage()); // Aquí viajará el texto "Stock insuficiente."
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}