package com.user.exceptions.validators;

import com.services.dtos.UserDto;
import com.services.enums.UserRol;
import com.user.exceptions.exceptions.BusinessRuleException;
import com.user.repository.UserRepository;
import com.user.security.dto.AuthRequest;
import org.springframework.http.HttpStatus;

public class UserValidators {

    public static void validateCreate(UserDto dto, UserRepository userRepository) throws BusinessRuleException {

        // Nombre
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new BusinessRuleException("1001", "Name is required", HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() < 3) {
            throw new BusinessRuleException("1002", "Name must have at least 3 characters", HttpStatus.BAD_REQUEST);
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new BusinessRuleException("1003", "Email is required", HttpStatus.BAD_REQUEST);
        }

        dto.setEmail(dto.getEmail().toLowerCase().trim());

        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BusinessRuleException("1004", "Invalid email format", HttpStatus.BAD_REQUEST);
        }

        var existingUser = userRepository.findByEmail(dto.getEmail());

        if (existingUser.isPresent() && !existingUser.get().getId().equals(dto.getId())) {
            throw new BusinessRuleException("1014", "Email already in use", HttpStatus.CONFLICT);
        }

        if (dto.getRol() == null) {
            throw new BusinessRuleException("1006", "Role is required", HttpStatus.BAD_REQUEST);
        }

        if (dto.getRol() != UserRol.ADMIN && dto.getRol() != UserRol.STUDENT) {
            throw new BusinessRuleException("1007", "Invalid role", HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new BusinessRuleException("1008", "Password is required", HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword().length() < 6) {
            throw new BusinessRuleException("1009", "Password must be at least 6 characters", HttpStatus.BAD_REQUEST);
        }
    }


    public static void validateUpdate(UserDto dto, UserRepository userRepository) throws BusinessRuleException {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new BusinessRuleException("1010", "Name is required", HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre().length() < 3) {
            throw new BusinessRuleException("1011", "Name must have at least 3 characters", HttpStatus.BAD_REQUEST);
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new BusinessRuleException("1012", "Email is required", HttpStatus.BAD_REQUEST);
        }

        dto.setEmail(dto.getEmail().toLowerCase().trim());

        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BusinessRuleException("1013", "Invalid email format", HttpStatus.BAD_REQUEST);
        }

        var existingUser = userRepository.findByEmail(dto.getEmail());

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessRuleException("1005", "Email already exists", HttpStatus.CONFLICT);
        }

        if (dto.getRol() == null) {
            throw new BusinessRuleException("1015", "Role is required", HttpStatus.BAD_REQUEST);
        }

        if (dto.getRol() != UserRol.ADMIN && dto.getRol() != UserRol.STUDENT) {
            throw new BusinessRuleException("1016", "Invalid role", HttpStatus.BAD_REQUEST);
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {

            if (dto.getPassword().length() < 6) {
                throw new BusinessRuleException("1017", "Password must be at least 6 characters", HttpStatus.BAD_REQUEST);
            }
        }
    }
    public static void validateCredentials(AuthRequest dto) throws BusinessRuleException {
        if (dto == null) {
            throw new BusinessRuleException("2005", "Credentials cannot be empty", HttpStatus.BAD_REQUEST);
        }
    }
}