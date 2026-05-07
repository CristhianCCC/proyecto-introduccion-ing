package com.services.dtos;
import com.services.enums.UserRol;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDto {

    private Long id;

    private String nombre;

    private String email;

    private String password;


    @Enumerated(EnumType.STRING)
    private UserRol rol;

    public UserDto() { }

    public UserDto(Long id, String email, String password,  String nombre, UserRol rol) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

