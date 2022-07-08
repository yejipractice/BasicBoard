package com.example.basicboard.service;

import com.example.basicboard.advice.exception.CUserNotFoundException;
import com.example.basicboard.dto.SigninRequestDto;
import com.example.basicboard.dto.SignupRequestDto;
import com.example.basicboard.dto.UserResponseDto;
import com.example.basicboard.models.User;
import com.example.basicboard.models.UserRole;
import com.example.basicboard.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Spy
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("테스트 준비")
    void mockito_test() {
        assertThat(userService).isNotNull();
    }


    @Test
    @DisplayName("사용자 회원 가입")
    public void saveNewUser() {

        SignupRequestDto requestDto = SignupRequestDto.builder()
                .userId("")
                .password("")
                .admin(false)
                .adminToken("")
                .build();

        User user = User.builder()
                .id(1)
                .userId(requestDto.getUserId())
                .password(requestDto.getPassword())
                .role(UserRole.USER)
                .build();


        when(userRepository.save(any())).thenReturn(user);

        // when
        User result = userService.signup(requestDto);

        verify(userRepository, times(1)).save(any());
        assertThat(result, equalTo(user));
    }

    @Test
    @DisplayName("관리자 회원 가입")
    public void saveNewAdmin() {

        SignupRequestDto requestDto = SignupRequestDto.builder()
                .userId("")
                .password("")
                .admin(true)
                .adminToken("AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC")
                .build();

        User user = User.builder()
                .id(2)
                .userId(requestDto.getUserId())
                .password(requestDto.getPassword())
                .role(UserRole.ADMIN)
                .build();


        when(userRepository.save(any())).thenReturn(user);

        // when
        User result = userService.signup(requestDto);

        verify(userRepository, times(1)).save(any());
        assertThat(result, equalTo(user));
    }

    @Test
    @DisplayName("사용자 단건 조회")
    public void getUser() {
        User user = User.builder()
                .id(1)
                .userId("user")
                .password("user")
                .role(UserRole.USER)
                .build();


        when(userRepository.findByUserId("user")).thenReturn(Optional.of(user));

        assertThat(userService.getUser(user.getUserId()).getUserId(), equalTo(user.getUserId()));
    }

    @Test
    @DisplayName("사용자 목록 조회")
    public void getUsers() {
        List<User> userList = new ArrayList<>();

        User user1 = User.builder()
                .id(1)
                .userId("user")
                .password("user")
                .role(UserRole.USER)
                .build();

        User user2 = User.builder()
                .id(2)
                .userId("admin")
                .password("admin")
                .role(UserRole.ADMIN)
                .build();

        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        assertThat(userService.getAllUsers(), equalTo(userList));
    }

    @Test
    @DisplayName("사용자 수정")
    public void modifyUser() {
        User user = User.builder()
                .id(1)
                .userId("user")
                .password("user")
                .role(UserRole.USER)
                .build();

        SigninRequestDto requestDto = new SigninRequestDto("modified", "modified");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.modifyUser(user.getId(), requestDto);

        verify(userRepository, times(1)).save(any());
        assertThat(userRepository.findById(user.getId()).get().getUserId(), equalTo(requestDto.getUserId()));

    }

    @Test
    @DisplayName("사용자 탈퇴")
    public void deleteUser() {
        User user = User.builder()
                .id(1)
                .userId("user")
                .password("user")
                .role(UserRole.USER)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());

        verify(userRepository).delete(any(User.class));
    }
}