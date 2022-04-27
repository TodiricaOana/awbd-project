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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto findUserById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found");
        return userMapper.mapToDto(user.get());
    }

    public User findById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("User not found");
        return user.get();
    }

    public void deleteUserById(Long id){
        User user = findById(id);
        if (user.getEmail().equals(jpaUserDetailsService.getCurrentUser().getUsername())) {
            SecurityContextHolder.clearContext();
        }
        userRepository.delete(user);
    }

    private boolean checkIfEmailExists(String email) {
        User user = userRepository.findUserByEmail(email);
        return user != null;
    }

    public UserDto findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    public UserDto createUser(UserDto userDto) throws EmailAlreadyUsedException {
        User user = userMapper.mapToEntity(userDto);

        if (checkIfEmailExists(user.getEmail())) {
            throw new EmailAlreadyUsedException("This email is already used");
        }

        Authority authority = authorityRepository.findByRole("ROLE_GUEST");
        user.setAuthorities(Collections.singleton(authority));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.mapToDto(savedUser);
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserDto> usersDTO = users.getContent().stream().map(user -> userMapper.mapToDto(user))
                .collect(Collectors.toList());
        return new PageImpl<>(usersDTO, pageable, users.getTotalElements());
    }

    public UserDto updateUser(User user, Long id)  {
        findById(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(id);
        return userMapper.mapToDto(userRepository.save(user));
    }
}
