package marketplace_la_u.marketplace_la_u.repository;
import marketplace_la_u.marketplace_la_u.model.Users;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);
}
