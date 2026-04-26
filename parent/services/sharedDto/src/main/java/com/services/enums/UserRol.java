package com.services.enums;

public enum UserRol {

    ADMIN("1"),
    STUDENT("2");

    private final String code;

    UserRol(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    // Opcional pero MUY útil
    public static UserRol fromCode(String code) {
        for (UserRol rol : UserRol.values()) {
            if (rol.code.equals(code)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Código de rol inválido: " + code);
    }
}