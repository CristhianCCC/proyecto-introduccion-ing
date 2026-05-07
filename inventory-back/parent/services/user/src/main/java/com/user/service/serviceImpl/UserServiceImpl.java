package com.user.service.serviceImpl;
import com.services.dtos.UserDto;
import com.user.entity.User;
import com.user.exceptions.exceptions.BusinessRuleException;
import com.user.exceptions.validators.UserValidators;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setNombre(user.getNombre());
        userDto.setEmail(user.getEmail());
        userDto.setRol(user.getRol());
        return userDto;
    }

    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setNombre(dto.getNombre());
        user.setEmail(dto.getEmail());
        user.setRol(dto.getRol());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return user;
    }

    @Override
    public UserDto getUserbyId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? mapToDto(user) : null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        UserValidators.validateCreate(userDto, userRepository);
        User user = mapToEntity(userDto);
        return mapToDto(userRepository.save(user));
    }

    @Override
    public UserDto putUser(Long id, UserDto userDto) {
        UserValidators.validateUpdate(userDto, userRepository);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNombre(userDto.getNombre());
        user.setEmail(userDto.getEmail());
        user.setRol(userDto.getRol());
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BusinessRuleException(
                    "8000", "User doesn't exist, please verify and try again", HttpStatus.BAD_REQUEST
            );
        }
        userRepository.delete(user.get());
    }
}