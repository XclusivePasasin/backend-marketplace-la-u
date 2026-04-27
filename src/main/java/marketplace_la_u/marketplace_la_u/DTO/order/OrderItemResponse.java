package marketplace_la_u.marketplace_la_u.DTO.order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double subtotal;
}
