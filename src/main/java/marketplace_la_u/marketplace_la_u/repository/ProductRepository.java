package marketplace_la_u.marketplace_la_u.repository;

import marketplace_la_u.marketplace_la_u.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

    @EntityGraph(attributePaths = {"user", "images"})
    @Override
    List<Product> findAll();

    @EntityGraph(attributePaths = {"user", "images"})
    @Override
    Optional<Product> findById(Integer id);
}
