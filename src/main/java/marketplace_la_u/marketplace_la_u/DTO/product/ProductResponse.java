package marketplace_la_u.marketplace_la_u.DTO.product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace_la_u.marketplace_la_u.model.Product;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Long categoryId;
    private Long userId;


    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();

        if(product.getCategory() != null){
            this.categoryId = product.getCategory().getId();
        }

        if(product.getUser() != null){
            this.userId = product.getUser().getId();
        }
    }
}
