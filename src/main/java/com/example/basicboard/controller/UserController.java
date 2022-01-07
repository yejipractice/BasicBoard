package com.example.basicboard.controller;

import com.example.basicboard.dto.SigninRequestDto;
import com.example.basicboard.models.User;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.models.response.ListResult;
import com.example.basicboard.models.response.SingleResult;
import com.example.basicboard.repository.UserRepository;
import com.example.basicboard.service.ResponseService;
import com.example.basicboard.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"2. 사용자 컨트롤러"})
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다.")
    @GetMapping("/users")
    public ListResult<User> findAllUsers() {
        return responseService.getListResult(userService.getAllUsers());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보를 조회한다.")
    @GetMapping("/user")
    public SingleResult<User> findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        return responseService.getSingleResult(userService.getUser(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = "/user")
    public CommonResult modifyUser(
            @ApiParam(value = "회원 번호", required = true) @RequestParam long id,
            @ApiParam(value = "회원 정보", required = true) @RequestBody SigninRequestDto signinRequestDto
            ) {
            userService.modifyUser(id, signinRequestDto);
            return responseService.getSuccessResult();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
    @DeleteMapping(value = "/user")
    public CommonResult deleteUser (@ApiParam(value = "회원 번호", required = true) @RequestParam long id) {
        userService.deleteUser(id);
        return responseService.getSuccessResult();
    }


}
