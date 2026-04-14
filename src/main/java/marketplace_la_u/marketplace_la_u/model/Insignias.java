package marketplace_la_u.marketplace_la_u.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insignias")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Insignias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String detalles;
}
