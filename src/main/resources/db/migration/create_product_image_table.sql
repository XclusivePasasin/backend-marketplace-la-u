-- ==================================================
-- MIGRACIÓN: Crear tabla product_image
-- Descripción: Tabla para almacenar múltiples imágenes por producto
-- Relación: 1-a-N (Product -> ProductImage)
-- Fecha: 2026-05-03
-- ==================================================

-- Eliminar tabla si existe (para recrearla con tipos correctos)
DROP TABLE IF EXISTS product_image;

CREATE TABLE product_image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    image_order INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign Key con ON DELETE CASCADE
    CONSTRAINT fk_product_image_product 
        FOREIGN KEY (product_id) 
        REFERENCES product(id) 
        ON DELETE CASCADE,
    
    -- Índice para optimizar búsquedas por product_id
    INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==================================================
-- COMENTARIOS DE CAMPOS
-- ==================================================
-- id: Primary Key autoincremental (INT para compatibilidad con Product.id)
-- product_id: FK a product.id (INT - ON DELETE CASCADE elimina imágenes si se borra el producto)
-- image_url: URL completa de la imagen (hasta 500 caracteres)
-- image_order: Orden de visualización (0 = primera, 1 = segunda, etc.)
-- created_at: Timestamp de creación automático
-- ==================================================
-- NOTA: Se usa INT en lugar de BIGINT porque Product.id es INTEGER
-- ==================================================
