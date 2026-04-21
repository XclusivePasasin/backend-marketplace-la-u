package marketplace_la_u.marketplace_la_u.controller;

import marketplace_la_u.marketplace_la_u.config.AuthenticatedUserMock;
import marketplace_la_u.marketplace_la_u.model.Product;
import marketplace_la_u.marketplace_la_u.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Endpoint para listar los productos (GET /api/products)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Endpoint para crear un producto (POST /api/products)
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        // Usamos directamente el método setUser_id que tienes en tu modelo
        product.setUser_id(AuthenticatedUserMock.USER_ID);

        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
}