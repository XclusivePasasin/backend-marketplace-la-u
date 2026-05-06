package marketplace_la_u.marketplace_la_u.DTO.order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace_la_u.marketplace_la_u.model.Orders;
import java.util.List;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime date;
    private Double total;
    private List<OrderProductResponse> products;

    public OrderResponse(Orders order){
    this.id = order.getId();
    this.date = order.getCreatedAt();
    this.total = order.getTotalPrice().doubleValue();
    this.products = order.getProducts().stream().map(OrderProductResponse::new).toList();

    }
}

