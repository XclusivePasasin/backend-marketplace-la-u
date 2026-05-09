package marketplace_la_u.marketplace_la_u.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${upload.path:uploads}")
    private String uploadPath;

    /**
     * Guarda un archivo en el sistema de archivos local y retorna la ruta del archivo guardado.
     * 
     * @param file El archivo MultipartFile a guardar
     * @return La ruta relativa del archivo guardado (nombre del archivo con extensión)
     * @throws IOException Si ocurre un error al guardar el archivo
     */
    public String saveFile(MultipartFile file) throws IOException {
        // Crear el directorio uploads si no existe
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Extraer la extensión del archivo original
        String extension = extractExtension(file.getOriginalFilename());

        // Generar un nombre único con UUID + extensión
        String fileName = UUID.randomUUID().toString() + extension;

        // Construir la ruta completa del archivo
        Path filePath = uploadDir.resolve(fileName);

        // Guardar el archivo
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retornar solo el nombre del archivo (para guardarlo en la base de datos)
        return fileName;
    }

    /**
     * Extrae la extensión del nombre del archivo original.
     * Si no tiene extensión o el nombre es nulo/vacío, retorna .webp por defecto.
     * 
     * @param originalFilename El nombre original del archivo
     * @return La extensión con el punto incluido (ej: .webp, .jpg, .png)
     */
    private String extractExtension(String originalFilename) {
        // Si el nombre es nulo o vacío, usar .webp por defecto
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            return ".webp";
        }

        // Buscar el último punto en el nombre del archivo
        int lastDotIndex = originalFilename.lastIndexOf('.');
        
        // Si no hay punto o está al final, usar .webp por defecto
        if (lastDotIndex == -1 || lastDotIndex == originalFilename.length() - 1) {
            return ".webp";
        }

        // Extraer y retornar la extensión (incluyendo el punto)
        String extension = originalFilename.substring(lastDotIndex).toLowerCase();
        
        // Si la extensión extraída está vacía, usar .webp por defecto
        return extension.isEmpty() ? ".webp" : extension;
    }

    /**
     * Elimina un archivo del sistema de archivos.
     * 
     * @param fileName El nombre del archivo a eliminar
     * @throws IOException Si ocurre un error al eliminar el archivo
     */
    public void deleteFile(String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }

        Path filePath = Paths.get(uploadPath).resolve(fileName);
        Files.deleteIfExists(filePath);
    }

    /**
     * Obtiene la ruta completa de un archivo.
     * 
     * @param fileName El nombre del archivo
     * @return La ruta completa del archivo
     */
    public Path getFilePath(String fileName) {
        return Paths.get(uploadPath).resolve(fileName);
    }
}
