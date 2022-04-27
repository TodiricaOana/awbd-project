package com.example.javaproject.service;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.definition.EmailAlreadyUsedException;
import com.example.javaproject.exception.definition.UserNotFoundException;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.security.Authority;
import com.example.javaproject.model.security.User;
import com.example.javaproject.repository.security.AuthorityRepository;
import com.example.javaproject.repository.security.UserRepository;
import com.example.javaproject.service.security.JpaUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String EMAIL = "dummy@gmail.com";
    private static final String ROLE = "ROLE_GUEST";

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JpaUserDetailsService jpaUserDetailsService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Create user - works")
    void create_works() throws EmailAlreadyUsedException {
        UserDto userDto = getUserDto();
        userDto.setPassword(passwordEncoder.encode("password"));

        Authority authority = getAuthority();

        User user = getUser();
        user.setPassword(passwordEncoder.encode("password"));
        user.setAuthorities(Collections.singleton(authority));

        User savedUser = getUser();
        savedUser.setId(ID);

        UserDto returnedUserDto = getUserDto();
        returnedUserDto.setId(ID);

        when(userMapper.mapToEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(returnedUserDto);
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(null);
        when(authorityRepository.findByRole(ROLE)).thenReturn(authority);

        UserDto result = userService.createUser(userDto);

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

        assertThrows(EmailAlreadyUsedException.class, () -> userService.createUser(userDto));
    }

    @Test
    @DisplayName("Update user - works")
    void update_works() {
        User user = getUser();
        user.setPassword(passwordEncoder.encode("password"));

        User userFind = getUser();
        userFind.setId(ID);
        userFind.setPassword(passwordEncoder.encode("password"));
        Optional<User> optionalUser = Optional.of(userFind);

        User savedUser = getUser();
        savedUser.setId(ID);
        savedUser.setPassword(passwordEncoder.encode("password"));

        UserDto returnedUserDto = getUserDto();
        returnedUserDto.setId(ID);
        returnedUserDto.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findById(ID)).thenReturn(optionalUser);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.mapToDto(savedUser)).thenReturn(returnedUserDto);

        UserDto result = userService.updateUser(user, ID);

        assertNotNull(result);
        assertEquals(returnedUserDto.getEmail(), result.getEmail());
        assertEquals(returnedUserDto.getId(), result.getId());
    }

    @Test
    @DisplayName("update user - fails")
    void update_userNotFound_fails() {
        User user = getUser();

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user, ID));
    }

    @Test
    @DisplayName("delete- works")
    void delete_works()  {
        User user = getUser();
        user.setId(ID);
        Optional<User> optionalUser = Optional.of(user);

        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                        EMAIL, "password", new HashSet<>());

        when(userRepository.findById(ID)).thenReturn(optionalUser);
        when(jpaUserDetailsService.getCurrentUser()).thenReturn(userDetails);

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

    private Authority getAuthority() {
        Authority authority = new Authority();
        authority.setRole(ROLE);

        return authority;
    }
}