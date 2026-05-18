package marketplace_la_u.marketplace_la_u.DTO.product;

import lombok.Data;
import marketplace_la_u.marketplace_la_u.model.Product;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Long categoryId;      // Agregado para el frontend
    private String categoryName;
    private String imgUrl;
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
        this.imgUrl = product.getImg_url();

        // Extraemos los datos del usuario que creó el producto
        this.userId = product.getUser().getId();
        this.userName = product.getUser().getName();
    }
}