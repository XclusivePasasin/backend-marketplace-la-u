package marketplace_la_u.marketplace_la_u.DTO.order;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotEmpty(message = "La orden debe tener minimo un producto")
    private List<OrderItemRequest> items;
}