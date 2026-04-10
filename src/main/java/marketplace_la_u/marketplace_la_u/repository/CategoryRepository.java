package marketplace_la_u.marketplace_la_u.repository;

import marketplace_la_u.marketplace_la_u.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNombre(String nombre);
}
