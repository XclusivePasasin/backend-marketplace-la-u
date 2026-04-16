package marketplace_la_u.marketplace_la_u.repository;
import marketplace_la_u.marketplace_la_u.orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
