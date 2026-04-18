package marketplace_la_u.marketplace_la_u.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo obligatorio.")
    @Column(nullable = false)
    private String name;

    @Email(message = "Correo invalido.")
    @NotBlank(message = "Campo obligatorio.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Campo obligatorio")
    @Size(min = 6, message = "Minimo 6 caracteres")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Campo obligatorio")
    @Column(unique = true)
    private String username;

    @Column
    private String gender;

    @Column
    private String profession;

    @Column
    private String imagen;

    @Column
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_mk_id")
    private Role roleMK;

    @Column(name = "active_status")
    private boolean activeStatus;

    @Column(name = "dark_mode")
    private  boolean darkMode;

    @Column(name = "messenger_color")
    private String messengerColor;

    @ManyToOne
    @JoinColumn(name = "universidad_id")
    @NotNull(message = "Campo obligatorio")
    private Universidades universidades;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    @NotNull(message = "Campo obligatorio.")
    private Carreras carreras;

    @ManyToOne
    @JoinColumn(name = "insignia_id")
    private Insignias insignias;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @NotBlank(message = "Campo obligatorio.")
    @Column(nullable = false)
    private String address;

    @Column
    private String role;

}
