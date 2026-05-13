package marketplace_la_u.marketplace_la_u.DTO.product;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List; // <-- Importación necesaria

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Campo obligatorio.")
    private String name;

    private Long userId;

    private String description;

    @Positive(message = "Campo invalido")
    private Double price;

    @Min(value = 0, message = "Campo invalido.")
    private Integer stock;

    @NotNull(message = "campo obligatorio.")
    private Long categoryId;

    // es para recibir el arreglo de fotos del frontend
    private List<String> images;
}