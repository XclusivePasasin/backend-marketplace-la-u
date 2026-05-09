package marketplace_la_u.marketplace_la_u.repository;

import marketplace_la_u.marketplace_la_u.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    
    /**
     * Obtiene todas las imágenes de un producto ordenadas por image_order
     * @param productId ID del producto
     * @return Lista de imágenes ordenadas
     */
    List<ProductImage> findByProductIdOrderByImageOrder(Integer productId);
}
