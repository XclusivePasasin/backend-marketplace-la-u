package marketplace_la_u.marketplace_la_u.repository;
import marketplace_la_u.marketplace_la_u.model.Order_Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepository extends JpaRepository<Order_Products, Long> {
}
