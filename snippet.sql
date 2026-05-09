-- 1. Ver tipos actuales
DESCRIBE product;
DESCRIBE product_image;

-- 2. Limpiar datos
DELETE FROM product_image;

-- 3. Eliminar constraint incorrecta
ALTER TABLE product_image DROP FOREIGN KEY FK6oo0cvcdtb6qmwsga468uuukk;

-- 4. Cambiar product_id de BIGINT a INT (para coincidir con product.id)
ALTER TABLE product_image MODIFY product_id INT;

-- 5. Recrear foreign key
ALTER TABLE product_image ADD CONSTRAINT FK6oo0cvcdtb6qmwsga468uuukk 
FOREIGN KEY (product_id) REFERENCES product(id);

-- 6. Verificar
DESCRIBE product_image;
