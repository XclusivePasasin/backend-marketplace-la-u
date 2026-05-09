package marketplace_la_u.marketplace_la_u.service;

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

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada." ));

        Users user = userRepository.findById(productDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado."));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        product.setStock(productDto.getStock());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setUser(user);
        
        // Rescatado de tu rama master: Por defecto, un producto nuevo está activo
        product.setStatus(true); 

        // 1. Guardamos el producto base en la DB para que se le asigne un ID
        Product savedProduct = productRepository.save(product);

        // 2. Lógica de múltiples imágenes movida desde el controlador
        if (productDto.getImages() != null && !productDto.getImages().isEmpty()) {
            // Asignamos la primera imagen como portada en la tabla principal
            savedProduct.setImg_url(productDto.getImages().get(0));
            // Guardamos la galería completa en la tabla product_image
            productImageService.saveImages(savedProduct, productDto.getImages());
            // Actualizamos el producto con su URL principal
            savedProduct = productRepository.save(savedProduct);
        } else {
            savedProduct.setImg_url("");
        }

        return new ProductResponse(savedProduct);
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