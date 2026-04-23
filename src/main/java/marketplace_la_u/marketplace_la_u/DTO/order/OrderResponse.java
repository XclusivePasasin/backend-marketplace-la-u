package marketplace_la_u.marketplace_la_u.DTO.order;

import java.util.List;
import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private LocalDateTime date;
    private Double total;
    private List<OrderItemResponse> items;
}
