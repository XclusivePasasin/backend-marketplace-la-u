package marketplace_la_u.marketplace_la_u.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import marketplace_la_u.marketplace_la_u.config.AuthenticatedUserMock;
import marketplace_la_u.marketplace_la_u.model.Product;
import marketplace_la_u.marketplace_la_u.model.ProductImage;
import marketplace_la_u.marketplace_la_u.service.ProductImageService;
import marketplace_la_u.marketplace_la_u.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Endpoint para listar los productos (GET /api/products)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Endpoint para obtener un producto por ID CON sus imágenes (GET /api/products/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Integer id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Obtener imágenes asociadas al producto
            List<ProductImage> productImages = productImageService.getProductImages(id);

            // Construir respuesta con todas las imágenes y usuario completo
            Map<String, Object> response = new HashMap<>();
            response.put("id", product.getId());
            response.put("name", product.getName());
            response.put("description", product.getDescription());
            response.put("price", product.getPrice());
            response.put("img_url", product.getImg_url());
            response.put("category_id", product.getCategory_id());
            response.put("stock", product.getStock());
            response.put("status", product.getStatus());
            response.put("user", product.getUser()); // Ahora retorna el objeto User completo

            // Agregar array de URLs de imágenes
            List<String> imageUrls = new ArrayList<>();
            for (ProductImage img : productImages) {
                imageUrls.add(img.getImageUrl());
            }
            response.put("images", imageUrls);

            logger.info("✅ Producto {} obtenido con {} imágenes", id, imageUrls.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("❌ Error obteniendo producto {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para crear un producto con múltiples imágenes (POST /api/products)
    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> request) {
        try {
            logger.info("🔵 Creando producto con múltiples imágenes...");

            // 1. Extraer img_url (puede ser JSON array de URLs)
            Object imgUrlObj = request.get("img_url");
            List<String> imageUrls = new ArrayList<>();

            // 2. Parsear img_url según su tipo
            if (imgUrlObj instanceof String) {
                String imgUrlString = (String) imgUrlObj;
                
                // Intentar parsear como JSON array
                if (imgUrlString.startsWith("[") && imgUrlString.endsWith("]")) {
                    try {
                        String[] urls = objectMapper.readValue(imgUrlString, String[].class);
                        imageUrls = Arrays.asList(urls);
                        logger.info("📸 Se parsearon {} URLs de imágenes desde JSON", imageUrls.size());
                    } catch (Exception e) {
                        logger.warn("⚠️ Error parseando JSON array de imágenes: {}", e.getMessage());
                        // Si falla el parseo, usar como URL simple
                        imageUrls.add(imgUrlString);
                    }
                } else if (!imgUrlString.isEmpty()) {
                    // Es una URL simple
                    imageUrls.add(imgUrlString);
                }
            } else if (imgUrlObj instanceof List) {
                // Ya es una lista
                imageUrls = (List<String>) imgUrlObj;
            }

            // 3. Crear objeto Product
            Product product = new Product();
            product.setName((String) request.get("name"));
            product.setDescription((String) request.get("description"));
            
            // Convertir price a Double
            Object priceObj = request.get("price");
            if (priceObj instanceof Number) {
                product.setPrice(((Number) priceObj).doubleValue());
            }
            
            // Convertir category_id a Integer
            Object categoryIdObj = request.get("category_id");
            if (categoryIdObj instanceof Number) {
                product.setCategory_id(((Number) categoryIdObj).intValue());
            }
            
            // Setear primera imagen en img_url para compatibilidad
            if (!imageUrls.isEmpty()) {
                product.setImg_url(imageUrls.get(0));
            } else {
                product.setImg_url("");
            }
            
            // Convertir stock a Integer
            Object stockObj = request.get("stock");
            if (stockObj instanceof Number) {
                product.setStock(((Number) stockObj).intValue());
            }
            
            // Convertir status a Boolean
            Object statusObj = request.get("status");
            if (statusObj instanceof Boolean) {
                product.setStatus((Boolean) statusObj);
            }
            
            // User desde AuthenticatedUserMock (creamos un objeto Users temporal)
            // TODO: En producción, obtener el Users de la sesión autenticada
            marketplace_la_u.marketplace_la_u.model.Users user = new marketplace_la_u.marketplace_la_u.model.Users();
            user.setId(Long.valueOf(AuthenticatedUserMock.USER_ID));
            product.setUser(user);

            // 4. Guardar el producto
            Product savedProduct = productService.saveProduct(product);
            logger.info("✅ Producto guardado con ID: {}", savedProduct.getId());

            // 5. Guardar todas las imágenes relacionadas
            if (!imageUrls.isEmpty()) {
                productImageService.saveImages(savedProduct, imageUrls);
                logger.info("✅ Se guardaron {} imágenes para el producto {}", 
                           imageUrls.size(), savedProduct.getId());
            }

            // 6. Construir respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedProduct.getId());
            response.put("name", savedProduct.getName());
            response.put("description", savedProduct.getDescription());
            response.put("price", savedProduct.getPrice());
            response.put("img_url", savedProduct.getImg_url());
            response.put("category_id", savedProduct.getCategory_id());
            response.put("stock", savedProduct.getStock());
            response.put("status", savedProduct.getStatus());
            response.put("user", savedProduct.getUser()); // Retornar objeto User completo
            response.put("images", imageUrls);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("❌ Error creando producto: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error creando producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}