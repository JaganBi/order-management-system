package com.example.OrderManagementSystem.Mapper;

import com.example.OrderManagementSystem.DTO.SignupRequest;
import com.example.OrderManagementSystem.DTO.UserResponse;
import com.example.OrderManagementSystem.Entity.User;

public class UserMapper {
    public static User toEntity(SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        return response;
    }
}
