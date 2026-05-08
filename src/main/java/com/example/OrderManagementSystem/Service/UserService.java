package com.example.OrderManagementSystem.Service;

import com.example.OrderManagementSystem.DTO.LoginRequest;
import com.example.OrderManagementSystem.DTO.LoginResponse;
import com.example.OrderManagementSystem.DTO.SignupRequest;
import com.example.OrderManagementSystem.DTO.UserResponse;
import com.example.OrderManagementSystem.Entity.User;
import com.example.OrderManagementSystem.Exception.InvalidCredentialsException;
import com.example.OrderManagementSystem.Exception.UserAlreadyExistsException;
import com.example.OrderManagementSystem.Mapper.UserMapper;
import com.example.OrderManagementSystem.Repository.UserRepository;
import com.example.OrderManagementSystem.Security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserResponse signup(SignupRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }
        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }
    public LoginResponse login(LoginRequest request) {
        User user = userRepository
                .findByEmail(request.getEmail()).
                orElseThrow(()->new
                        InvalidCredentialsException("Invalid email or password"));
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String token = JwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
