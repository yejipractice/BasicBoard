package com.example.basicboard.dto;

import com.example.basicboard.models.User;
import com.example.basicboard.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String userId;
    private UserRole role;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.role = user.getRole();
    }
}
