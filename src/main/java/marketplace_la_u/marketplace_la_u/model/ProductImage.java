package marketplace_la_u.marketplace_la_u.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_image")
public class ProductImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore  // Evita serialización circular Product ↔ ProductImage
    private Product product;
    
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;
    
    @Column(name = "image_order")
    private Integer imageOrder;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // --- CONSTRUCTORES ---
    
    public ProductImage() {
    }
    
    public ProductImage(String imageUrl, Integer imageOrder) {
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
        this.createdAt = LocalDateTime.now();
    }
    
    // --- GETTERS Y SETTERS ---
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Integer getImageOrder() {
        return imageOrder;
    }
    
    public void setImageOrder(Integer imageOrder) {
        this.imageOrder = imageOrder;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
