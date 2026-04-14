package marketplace_la_u.marketplace_la_u.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_products")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order_Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int quantity;

    @Column(name = "price_purchase", precision = 10, scale = 2)
    private BigDecimal pricePurchase;
}
