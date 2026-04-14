package marketplace_la_u.marketplace_la_u.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "personal_access_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Personal_Access_Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tokenable_id")
    private Users tokenable;

    @Column(nullable = false)
    private String tokenableType;

    @Column(nullable = false)
    private String token;

    @Column(name = "last_used_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime lastUsedAt;
}
