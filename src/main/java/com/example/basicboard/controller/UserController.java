package com.example.basicboard.controller;

import com.example.basicboard.dto.SigninRequestDto;
import com.example.basicboard.dto.SignupRequestDto;
import com.example.basicboard.models.User;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.models.response.SingleResult;
import com.example.basicboard.service.ResponseService;
import com.example.basicboard.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @PostMapping("/user/signin")
    public SingleResult<String> signIn(@RequestBody SigninRequestDto signinRequestDto) {
        String token = userService.signin(signinRequestDto.getUserId(), signinRequestDto.getPassword());
        System.out.println(token);
        return responseService.getSingleResult(token);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping("/user/signup")
    public CommonResult signUp(@RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.signup(signupRequestDto);
        return responseService.getSuccessResult();
    }

}
