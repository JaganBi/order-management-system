package com.example.OrderManagementSystem.Controller;

import com.example.OrderManagementSystem.DTO.LoginRequest;
import com.example.OrderManagementSystem.DTO.SignupRequest;
import com.example.OrderManagementSystem.DTO.UserResponse;
import com.example.OrderManagementSystem.Entity.User;
import com.example.OrderManagementSystem.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public UserResponse signup(@RequestBody SignupRequest request) {
        return userService.signup(request);
    }
    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
