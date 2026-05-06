package marketplace_la_u.marketplace_la_u.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace_la_u.marketplace_la_u.DTO.product.ProductRequest;
import marketplace_la_u.marketplace_la_u.DTO.product.ProductResponse;
import marketplace_la_u.marketplace_la_u.DTO.product.ProductUpdateRequest;
import marketplace_la_u.marketplace_la_u.config.AuthenticatedUserMock;
import marketplace_la_u.marketplace_la_u.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AuthenticatedUserMock authenticatedUserMock;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProducts(@RequestBody @Valid ProductRequest productDto) {
        Long userIdLog = authenticatedUserMock.getAuthenticatedUserId();
        productDto.setUserId(userIdLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProducts(productDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProducts(@PathVariable Long id,@RequestBody @Valid ProductUpdateRequest productDto) {
        Long userId = authenticatedUserMock.getAuthenticatedUserId();
        return ResponseEntity.ok(productService.updateProducts(id, productDto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id) {
        Long userId = authenticatedUserMock.getAuthenticatedUserId();
        productService.deleteProducts(id, userId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> consultById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.consultById(id));
    }
}