package marketplace_la_u.marketplace_la_u.DTO.product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Campo obligatorio")
    private String name;

    private String description;

    @NotNull(message = "Campo obligatorio")
    @DecimalMin(value = "0.01", message = "Precio debe ser mayor a 0")
    private Double price;

    @Min(value = 0, message = "Campo invalido")
    private Integer stock;

    @NotNull(message = "campo obligatorio")
    private Long categoryId;
}
