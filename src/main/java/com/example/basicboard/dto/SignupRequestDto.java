package com.example.basicboard.dto;

import lombok.*;

@Builder
@Getter
@Setter
public class SignupRequestDto {
    private String userId;
    private String password;
    private boolean admin;
    private String adminToken;

}

