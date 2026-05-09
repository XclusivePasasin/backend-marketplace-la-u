package marketplace_la_u.marketplace_la_u.repository;
import marketplace_la_u.marketplace_la_u.model.Universidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversidadesRepository extends JpaRepository<Universidades, Long> {
}
