package marketplace_la_u.marketplace_la_u.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "universidades")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Universidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @Column
    private String dominio;

    @Column
    private String siglas;

    @Column
    private String color;

}
