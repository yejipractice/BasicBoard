package com.example.basicboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String userId;
    private String password;
    private boolean admin;
    private String adminToken;

}

