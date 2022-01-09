package com.example.javaproject.service;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.definition.EmailAlreadyUsedException;
import com.example.javaproject.exception.definition.UserNotFoundException;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.User;
import com.example.javaproject.model.UserType;
import com.example.javaproject.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "dummy@gmail.com";

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Create user - works")
    void create_works() throws EmailAlreadyUsedException, NoSuchAlgorithmException {
        UserDto userDto = getUserDto();
        User user = getUser();
        user.setPassword("password");

        User savedUser = getUser();
        savedUser.setId(ID);

        UserDto returnedUserDto = getUserDto();
        returnedUserDto.setId(ID);

        when(userMapper.mapToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(returnedUserDto);
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(null);

        UserDto result = userService.createUser(userDto, UserType.USER);

        assertNotNull(result);
        assertEquals(returnedUserDto.getEmail(), result.getEmail());
        assertEquals(returnedUserDto.getId(), result.getId());
    }

    @Test
    @DisplayName("Create user - fails")
    void create_existingEmail_fails() {
        UserDto userDto = getUserDto();
        User user = getUser();
        user.setPassword("password");

        when(userMapper.mapToEntity(userDto)).thenReturn(user);
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(new User());

        assertThrows(EmailAlreadyUsedException.class, () -> userService.createUser(userDto, UserType.USER));
    }

    @Test
    @DisplayName("Update user - works")
    void update_works() throws NoSuchAlgorithmException {
        User user = getUser();
        user.setPassword("password");

        User userFind = getUser();
        userFind.setId(ID);
        Optional<User> optionalUser = Optional.of(userFind);

        User savedUser = getUser();
        savedUser.setId(ID);

        UserDto returnedUserDto = getUserDto();
        returnedUserDto.setId(ID);

        when(userRepository.findById(ID)).thenReturn(optionalUser);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(returnedUserDto);

        UserDto result = userService.updateUser(user, ID, UserType.USER);

        assertNotNull(result);
        assertEquals(returnedUserDto.getEmail(), result.getEmail());
        assertEquals(returnedUserDto.getId(), result.getId());
    }

    @Test
    @DisplayName("update user - fails")
    void update_userNotFound_fails() {
        User user = getUser();

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user, ID,UserType.USER));
    }

    @Test
    @DisplayName("delete- works")
    void delete_works()  {
        User user = getUser();
        user.setId(ID);
        Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(ID)).thenReturn(optionalUser);

        userService.deleteUserById(ID);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("delete- fails")
    void delete_fails()  {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(ID));
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .email(EMAIL)
                .build();
    }

    private User getUser() {
        User user = new User();
        user.setEmail(EMAIL);

        return user;
    }
}