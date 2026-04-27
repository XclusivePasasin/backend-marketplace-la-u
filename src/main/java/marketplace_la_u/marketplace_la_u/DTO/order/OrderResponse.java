package marketplace_la_u.marketplace_la_u.DTO.order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime date;
    private Double total;
    private List<OrderItemResponse> items;
}
