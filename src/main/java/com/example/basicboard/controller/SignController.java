package com.example.basicboard.controller;

import com.example.basicboard.dto.SigninRequestDto;
import com.example.basicboard.dto.SignupRequestDto;
import com.example.basicboard.models.User;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.models.response.SingleResult;
import com.example.basicboard.service.ResponseService;
import com.example.basicboard.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"1. 계정 컨트롤러"})
@RequiredArgsConstructor
public class SignController {

    private final UserService userService;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping("/user/signin")
    public SingleResult<String> signIn(@RequestBody SigninRequestDto signinRequestDto) {
        String token = userService.signin(signinRequestDto.getUserId(), signinRequestDto.getPassword());
        System.out.println(token);
        return responseService.getSingleResult(token);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping("/user/signup")
    public CommonResult signUp(@RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.signup(signupRequestDto);
        return responseService.getSuccessResult();
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(String code) throws JSONException {
        userService.kakaoLogin(code);
        return "redirect:/";
    }
}
