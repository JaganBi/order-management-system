package com.example.OrderManagementSystem.DTO;

import lombok.Data;

@Data
public class SignupRequest {
    public String name;
    public String email;
    public String password;
}
