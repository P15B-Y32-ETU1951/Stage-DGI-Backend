package com.example.Angular.dto;

import lombok.Data;

@Data
public class PasswordWordRequest {
    int id;
    String password;
    String confirmpassword;
}
