package com.user.service;
import com.services.dtos.UserDto;

public interface UserService {

    public UserDto getUserbyId (Long userId);

    public UserDto getUserByEmail (String email);

    public UserDto postUser (UserDto userDto);

    public UserDto putUser (Long id, UserDto userDto);

    public void deleteUser (Long id);

}
