package com.example.OrderManagementSystem.Service;

import com.example.OrderManagementSystem.DTO.LoginRequest;
import com.example.OrderManagementSystem.DTO.SignupRequest;
import com.example.OrderManagementSystem.DTO.UserResponse;
import com.example.OrderManagementSystem.Entity.User;
import com.example.OrderManagementSystem.Exception.InvalidCredentialsException;
import com.example.OrderManagementSystem.Exception.UserAlreadyExistsException;
import com.example.OrderManagementSystem.Mapper.UserMapper;
import com.example.OrderManagementSystem.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserResponse signup(SignupRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }
        User user = UserMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
    public UserResponse login(LoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail()).
                orElseThrow(()->new
                        InvalidCredentialsException("Invalid email or password"));
        if(!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return UserMapper.toResponse(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
