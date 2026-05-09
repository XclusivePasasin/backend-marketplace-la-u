package marketplace_la_u.marketplace_la_u.DTO.order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductRequest {

    @NotNull(message = "Campo obligatorio")
    private Long productId;

    @Min(value = 1, message = "La cantidad minima es 1")
    private Integer quantity;
}
