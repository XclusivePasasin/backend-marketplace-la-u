package marketplace_la_u.marketplace_la_u.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuración web para servir archivos estáticos (imágenes) y CORS.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path:uploads}")
    private String uploadPath;

    /**
     * Configuración para servir imágenes estáticas desde la carpeta uploads.
     * 
     * Las imágenes serán accesibles públicamente en:
     * http://localhost:8080/images/{filename}.webp
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtener la ruta absoluta del directorio uploads
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        String uploadLocation = uploadDir.toUri().toString();

        // Mapear /images/** a la carpeta uploads
        registry
            .addResourceHandler("/images/**")
            .addResourceLocations(uploadLocation)
            .setCachePeriod(3600); // Cache por 1 hora (opcional)
    }

    /**
     * Configuración CORS para permitir requests desde el frontend Vue 3.
     * 
     * En producción, cambiar origins = "*" por la URL específica del frontend.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
