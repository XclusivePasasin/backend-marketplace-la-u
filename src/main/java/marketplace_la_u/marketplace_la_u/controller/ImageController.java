package marketplace_la_u.marketplace_la_u.controller;

import marketplace_la_u.marketplace_la_u.dto.ImageUploadResponse;
import marketplace_la_u.marketplace_la_u.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    // Tamaño máximo: 5MB en bytes
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB = 5242880 bytes

    // Tamaño mínimo: 1KB
    private static final long MIN_FILE_SIZE = 1024; // 1KB

    // Tipos MIME permitidos
    private static final String[] ALLOWED_MIME_TYPES = {
        "image/webp",
        "image/jpeg",
        "image/jpg",
        "image/png"
    };

    @Autowired
    private StorageService storageService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    /**
     * Endpoint para subir imágenes WebP optimizadas desde el frontend.
     * 
     * POST /api/images/upload
     * 
     * @param file Archivo MultipartFile (imagen WebP, JPEG o PNG)
     * @return ResponseEntity con la URL pública de la imagen subida
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        
        logger.info("🔵 Iniciando carga de imagen...");

        try {
            // 1. Validar que el archivo no sea null o esté vacío
            if (file == null || file.isEmpty()) {
                logger.warn("⚠️ Archivo vacío o null");
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("El archivo está vacío o no fue enviado"));
            }

            // 2. Validar tamaño mínimo (1KB)
            if (file.getSize() < MIN_FILE_SIZE) {
                logger.warn("⚠️ Archivo demasiado pequeño: {} bytes", file.getSize());
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("El archivo es demasiado pequeño. Mínimo: 1KB"));
            }

            // 3. Validar tamaño máximo (5MB)
            if (file.getSize() > MAX_FILE_SIZE) {
                logger.warn("⚠️ Archivo demasiado grande: {} bytes", file.getSize());
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("El archivo excede el tamaño máximo permitido (5MB)"));
            }

            // 4. Validar tipo MIME (Content-Type)
            String contentType = file.getContentType();
            if (contentType == null || !isValidMimeType(contentType)) {
                logger.warn("⚠️ Tipo de archivo no permitido: {}", contentType);
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse("Tipo de archivo no permitido. Solo se aceptan: WebP, JPEG, PNG"));
            }

            logger.info("✅ Validaciones pasadas. Content-Type: {}, Size: {} bytes", 
                       contentType, file.getSize());

            // 5. Guardar el archivo usando StorageService (genera UUID + extensión)
            String fileName = storageService.saveFile(file);

            // 6. Construir URL pública
            String publicUrl = baseUrl + "/images/" + fileName;

            logger.info("✅ Imagen guardada exitosamente: {}", publicUrl);

            // 7. Retornar response con la URL
            ImageUploadResponse response = new ImageUploadResponse(publicUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Error al subir imagen: {}", e.getMessage(), e);
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Error interno al procesar la imagen: " + e.getMessage()));
        }
    }

    /**
     * Valida si el tipo MIME está en la lista de permitidos.
     */
    private boolean isValidMimeType(String contentType) {
        for (String allowedType : ALLOWED_MIME_TYPES) {
            if (allowedType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Crea un objeto de respuesta de error con formato consistente.
     */
    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }
}
