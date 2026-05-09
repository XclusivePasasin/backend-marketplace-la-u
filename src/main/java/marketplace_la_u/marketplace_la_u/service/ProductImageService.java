package marketplace_la_u.marketplace_la_u.service;

import marketplace_la_u.marketplace_la_u.model.Product;
import marketplace_la_u.marketplace_la_u.model.ProductImage;
import marketplace_la_u.marketplace_la_u.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    
    @Autowired
    private ProductImageRepository productImageRepository;
    
    /**
     * Guarda múltiples imágenes para un producto.
     * 
     * @param product El producto al que se asociarán las imágenes
     * @param imageUrls Lista de URLs de imágenes
     */
    public void saveImages(Product product, List<String> imageUrls) {
        int order = 0;
        for (String url : imageUrls) {
            ProductImage image = new ProductImage(url, order);
            image.setProduct(product);
            productImageRepository.save(image);
            order++;
        }
    }
    
    /**
     * Obtiene todas las imágenes de un producto ordenadas por image_order.
     * 
     * @param productId ID del producto
     * @return Lista de imágenes ordenadas
     */
    public List<ProductImage> getProductImages(Integer productId) {
        return productImageRepository.findByProductIdOrderByImageOrder(productId);
    }
    
    /**
     * Elimina todas las imágenes de un producto.
     * 
     * @param productId ID del producto
     */
    public void deleteProductImages(Integer productId) {
        List<ProductImage> images = getProductImages(productId);
        productImageRepository.deleteAll(images);
    }
}
