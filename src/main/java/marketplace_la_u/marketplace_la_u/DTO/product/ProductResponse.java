package marketplace_la_u.marketplace_la_u.DTO.product;

import lombok.Data;
import marketplace_la_u.marketplace_la_u.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;      // Agregado para el frontend
    private String categoryName;
    private String imgUrl;        // La imagen principal
    private List<String> images;  // Agregado: Lista completa de imágenes para Vue
    private Long userId;          // Agregado para el frontend
    private String userName;      // Agregado para mostrar el vendedor

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice().doubleValue();
        this.stock = product.getStock();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();

        String rawImgUrl = product.getImg_url();
        if (rawImgUrl != null && !rawImgUrl.isEmpty()) {
            // Dividimos el texto que guardamos con comas en un Array de textos
            this.images = Arrays.asList(rawImgUrl.split(","));
            // Definimos la primera imagen como la "imgUrl" principal de portada
            this.imgUrl = this.images.get(0);
        } else {
            // Si el producto no tiene fotos, mandamos listas vacías para que Vue no explote
            this.images = new ArrayList<>();
            this.imgUrl = "";
        }

        // Extraemos los datos del usuario que creó el producto
        this.userId = product.getUser().getId();
        this.userName = product.getUser().getName();
    }
}