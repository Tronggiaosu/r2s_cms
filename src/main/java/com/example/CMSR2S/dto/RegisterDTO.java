package com.example.CMSR2S.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
