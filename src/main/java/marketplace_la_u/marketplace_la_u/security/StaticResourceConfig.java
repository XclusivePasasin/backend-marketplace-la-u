package marketplace_la_u.marketplace_la_u.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    // Extraemos la misma variable exacta que usa tu StorageService
    @Value("${upload.path:uploads}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Convertimos la ruta de la carpeta "uploads" a una ruta absoluta de tu disco duro
        String absolutePath = Paths.get(uploadPath).toAbsolutePath().toUri().toString();

        // Le decimos a Spring: "Cuando alguien pida una URL con /images/, ve a buscar el archivo a la carpeta uploads"
        registry.addResourceHandler("/images/**")
                .addResourceLocations(absolutePath);
    }
}