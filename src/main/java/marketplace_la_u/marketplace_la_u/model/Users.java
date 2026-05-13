package marketplace_la_u.marketplace_la_u.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

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
    private Boolean activeStatus;

    @Column(name = "dark_mode")
    private Boolean darkMode;

    @Column(name = "messenger_color")
    private String messengerColor;

    @ManyToOne
    @JoinColumn(name = "universidad_id")
    private Universidades universidades;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
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

    @Column
    private String address;

    @Column
    private String role;

    // --- MÉTODOS DE USERDETAILS PARA SPRING SECURITY ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por ahora le daremos un rol por defecto a todos los usuarios
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Si tu base de datos guarda null, asumimos que está activo
        return activeStatus == null || activeStatus;
    }
}