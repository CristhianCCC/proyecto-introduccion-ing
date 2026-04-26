package com.user.entity;
import com.services.enums.UserRol;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRol rol;

    public User() { }

    public User(String email, String password,  Long id, String nombre, UserRol rol) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UserRol getRol() {
        return rol;
    }

    public void setRol(UserRol rol) {
        this.rol = rol;
    }
}