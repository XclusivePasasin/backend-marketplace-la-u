package marketplace_la_u.marketplace_la_u.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obligatorio.")
    @Column
    private String serial;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private Users user;

    @Column
    @NotBlank(message = "Campo obligatorio.")
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    @Min(value = 0, message = "Campo obligatorio.")
    private BigDecimal price;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Seleccionar categoria obligatorio.")
    private Category category;

    @Column
    private String img_url;

    @Column
    private Boolean status;

    @Column
    private int stock;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @CreationTimestamp
    private LocalDateTime updateAt;
}
