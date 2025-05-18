package com.example.lecture11demo.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank (message = "Student number is required")
    private String studentNo;

    @NotBlank (message = "Password is required")
    private String password;
}
