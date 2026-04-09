package marketplace_la_u.marketplace_la_u.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class Users {
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
    private boolean activeStatus;

    @Column(name = "dark_mode")
    private  boolean darkMode;

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

    public int getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRoleMK() {
        return roleMK;
    }

    public void setRoleMK(Role roleMK) {
        this.roleMK = roleMK;
    }

    public boolean isActive_status() {
        return active_status;
    }

    public void setActive_status(boolean active_status) {
        this.active_status = active_status;
    }

    public boolean isDark_mode() {
        return dark_mode;
    }

    public void setDark_mode(boolean dark_mode) {
        this.dark_mode = dark_mode;
    }

    public String getMeseenger_color() {
        return meseenger_color;
    }

    public void setMeseenger_color(String meseenger_color) {
        this.meseenger_color = meseenger_color;
    }

    public Universidades getUniversidades() {
        return universidades;
    }

    public void setUniversidades(Universidades universidades) {
        this.universidades = universidades;
    }

    public Carreras getCarreras() {
        return carreras;
    }

    public void setCarreras(Carreras carreras) {
        this.carreras = carreras;
    }

    public Insignias getInsignias() {
        return insignias;
    }

    public void setInsignias(Insignias insignias) {
        this.insignias = insignias;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
