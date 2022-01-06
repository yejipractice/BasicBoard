package com.example.basicboard.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(nullable = true)
    private Long kakaoId;

    public User(String userId, String password, UserRole role){
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public void update(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
