package marketplace_la_u.marketplace_la_u.DTO.order;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace_la_u.marketplace_la_u.model.Order_Products;

@Data
@NoArgsConstructor
public class OrderProductResponse {
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double subtotal;

    public OrderProductResponse(Order_Products orderProducts) {
        this.productName = orderProducts.getProduct().getName();
        this.unitPrice = orderProducts.getPricePurchase().doubleValue();
        this.quantity = orderProducts.getQuantity();
        this.subtotal = orderProducts.getPricePurchase().doubleValue() * orderProducts.getQuantity();
    }
}
