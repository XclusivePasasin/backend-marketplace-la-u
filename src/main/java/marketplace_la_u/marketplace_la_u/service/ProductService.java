package marketplace_la_u.marketplace_la_u.service; // <-- Corregido a .service

import marketplace_la_u.marketplace_la_u.model.Product;
import marketplace_la_u.marketplace_la_u.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Obtener todos los productos para el Feed del Marketplace
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Obtener un producto por ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    // Guardar un nuevo producto
    public Product saveProduct(Product product) {
        // Por defecto, un producto nuevo está activo/disponible
        product.setStatus(true);
        return productRepository.save(product);
    }
}