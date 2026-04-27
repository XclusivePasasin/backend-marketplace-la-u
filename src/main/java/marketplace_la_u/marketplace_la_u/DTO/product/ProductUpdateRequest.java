package marketplace_la_u.marketplace_la_u.DTO.product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductUpdateRequest {
    private String name;

    private String description;

    @Positive(message = "Campo invalido.")
    private Double price;

    @Min(value = 0, message = "Campo invalido.")
    private Integer stock;

    private Long categoryId;

}

