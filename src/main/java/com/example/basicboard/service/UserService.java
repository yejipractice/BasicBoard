package com.example.basicboard.service;

import com.example.basicboard.advice.exception.CAccountExistedException;
import com.example.basicboard.advice.exception.CAdminTokenException;
import com.example.basicboard.advice.exception.CSigninFailedException;
import com.example.basicboard.advice.exception.CUserNotFoundException;
import com.example.basicboard.dto.SigninRequestDto;
import com.example.basicboard.dto.SignupRequestDto;
import com.example.basicboard.dto.UserResponseDto;
import com.example.basicboard.models.User;
import com.example.basicboard.models.UserRole;
import com.example.basicboard.repository.UserRepository;
import com.example.basicboard.security.JwtTokenProvider;
import com.example.basicboard.security.kakao.KakaoOAuth2;
import com.example.basicboard.security.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final KakaoOAuth2 kakaoOAuth2;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public String  signin(String id, String password) {
        User user = userRepository.findByUserId(id).orElseThrow(CSigninFailedException::new);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CSigninFailedException();
        }
        return jwtTokenProvider.createToken(user.getUserId(), user.getRole());
    }

    public void kakaoLogin(String authorizedCode) throws JSONException {
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = userInfo.getId();
        String email = userInfo.getEmail();

        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        if(kakaoUser == null){
            String password = passwordEncoder.encode(email);
            UserRole role = UserRole.USER;

            kakaoUser = new User(email, password, role, kakaoId);
            userRepository.save(kakaoUser);
        }

        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(email, email);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public User signup(SignupRequestDto signupRequestDto){
        Optional<User> found = userRepository.findByUserId(signupRequestDto.getUserId());
        if(found.isPresent()){
            throw new CAccountExistedException();
        }
        String encodedPw = passwordEncoder.encode(signupRequestDto.getPassword());
        UserRole role = UserRole.USER;
        if(signupRequestDto.isAdmin()){
            if(!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new CAdminTokenException();
            }
            role = UserRole.ADMIN;
        }

        User user = new User(signupRequestDto.getUserId(), encodedPw, role);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponseDto getUser(String id) {
        User user = userRepository.findByUserId(id).orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    public void modifyUser(long id, SigninRequestDto signinRequestDto) {
        String encodedPw = passwordEncoder.encode(signinRequestDto.getPassword());
        User user = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
        user.update(signinRequestDto.getUserId(), encodedPw);
        userRepository.save(user);
    }

    public void deleteUser(long id){
        User user = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
        userRepository.delete(user);
    }

}
