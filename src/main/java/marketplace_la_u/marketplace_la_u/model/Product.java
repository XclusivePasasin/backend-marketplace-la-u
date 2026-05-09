package marketplace_la_u.marketplace_la_u.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String serial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private Users user;

    @Column
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

    @Column(columnDefinition = "LONGTEXT")
    private String img_url;

    @Column
    private Boolean status;

    @Column
    private Integer stock;

    // Relación 1-a-N con ProductImage
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;
}