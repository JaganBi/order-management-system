package com.example.OrderManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
    @AllArgsConstructor
    public class ErrorResponseDTO {
        private String message;
        private int status;
    }
