package com.example.javaproject.service;

import com.example.javaproject.dto.UserDto;
import com.example.javaproject.exception.definition.EmailAlreadyUsedException;
import com.example.javaproject.exception.definition.UserNotFoundException;
import com.example.javaproject.mapper.UserMapper;
import com.example.javaproject.model.User;
import com.example.javaproject.model.UserType;
import com.example.javaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

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

        userRepository.delete(user);
    }

    private boolean checkIfEmailExists(String email) {
        User user = userRepository.findUserByEmail(email);
        return user != null;
    }

    private String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    public UserDto createUser(UserDto userDto, UserType type) throws EmailAlreadyUsedException, NoSuchAlgorithmException {
        User user = userMapper.mapToEntity(userDto);
        user.setUserType(type);

        if (checkIfEmailExists(user.getEmail())) {
            throw new EmailAlreadyUsedException("This email is already used");
        }

        user.setPassword(encrypt(user.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.mapToDto(savedUser);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List <UserDto> usersDTO = users.stream().map(user -> userMapper.mapToDto(user))
                .collect(Collectors.toList());
        return usersDTO;
    }

    public UserDto updateUser(User user, Long id,  UserType type) throws NoSuchAlgorithmException {
        findUserById(id);
        user.setId(id);
        user.setUserType(type);
        user.setPassword(encrypt(user.getPassword()));
        return userMapper.mapToDto(userRepository.save(user));
    }
}
