package marketplace_la_u.marketplace_la_u.service;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.product.ProductRequest;
import marketplace_la_u.marketplace_la_u.DTO.product.ProductResponse;
import marketplace_la_u.marketplace_la_u.DTO.product.ProductUpdateRequest;
import marketplace_la_u.marketplace_la_u.model.*;
import marketplace_la_u.marketplace_la_u.repository.CategoryRepository;
import marketplace_la_u.marketplace_la_u.repository.ProductRepository;
import marketplace_la_u.marketplace_la_u.repository.UsersRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository userRepository;
    
    // Inyectamos el servicio de imágenes que programaste
    private final ProductImageService productImageService;

    public ProductResponse createProducts(ProductRequest productDto) {
        // 1. Validaciones de Categoría y Usuario (JWT)
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada."));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        // 2. Crear instancia del producto
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        product.setStock(productDto.getStock());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setUser(user);
        product.setStatus(true);

        // 3. PROCESAMIENTO DE IMÁGENES (Limpieza de URL)
        if (productDto.getImages() != null && !productDto.getImages().isEmpty()) {
            String fullUrl = productDto.getImages().get(0);

            // Extraemos solo el nombre del archivo (ej: de "http://.../foto.webp" a "foto.webp")
            String fileName = fullUrl.substring(fullUrl.lastIndexOf("/") + 1);

            // Guardamos solo el nombre o la ruta relativa interna
            product.setImg_url(fileName);

            // Guardamos el producto para tener ID antes de la galería
            Product savedProduct = productRepository.save(product);
            productImageService.saveImages(savedProduct, productDto.getImages());
            return new ProductResponse(savedProduct);
        } else {
            product.setImg_url("");
            return new ProductResponse(productRepository.save(product));
        }
    }

    public List<ProductResponse> listProducts(){
        return productRepository.findAll().stream().map(ProductResponse::new).toList();
    }

    public void deleteProducts(Long id, Long userId){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        if (!product.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("No tienes permiso para eliminar el producto.");
        }

        productRepository.delete(product);
    }

    public ProductResponse consultById(Long id){
        return productRepository.findById(id).map(ProductResponse::new)
                .orElseThrow(()-> new EntityNotFoundException("Producto no encontrado."));
    }

    public ProductResponse updateProducts(Long id, ProductUpdateRequest nuevosDatos, Long userId) {
        return productRepository.findById(id).map(product -> {

            if (!product.getUser().getId().equals(userId)){
                throw new AccessDeniedException("No tienes permiso para modificar el producto.");
            }

            if (nuevosDatos.getName() != null) product.setName(nuevosDatos.getName());
            if (nuevosDatos.getDescription() != null) product.setDescription(nuevosDatos.getDescription());
            if (nuevosDatos.getPrice() != null) product.setPrice(BigDecimal.valueOf(nuevosDatos.getPrice()));
            if (nuevosDatos.getStock() != null) product.setStock(nuevosDatos.getStock());

            if (nuevosDatos.getCategoryId() != null) {
                Category category = categoryRepository.findById(nuevosDatos.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada."));
                product.setCategory(category);
            }

            return new ProductResponse(productRepository.save(product));
        }).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado."));
    }
}