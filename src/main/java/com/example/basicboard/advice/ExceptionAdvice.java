package com.example.basicboard.advice;

import com.example.basicboard.advice.exception.*;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e){
        return responseService.getFailResult();
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("해당 계정이 존재하지 않거나 잘못된 계정입니다.");
    }

    @ExceptionHandler(CPostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult postNotFoundException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("해당 게시글이 존재하지 않습니다.");
    }

    @ExceptionHandler(CSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult siginFailedException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("계정 정보가 옳지 않습니다.");
    }

    @ExceptionHandler(CAccountExistedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accountExistedException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("중복된 계정 정보가 존재합니다.");
    }

    @ExceptionHandler(CAdminTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult adminTokenNotMatchedException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("관리자 암호가 옳지 않습니다.");
    }

    @ExceptionHandler(CNotPostUserException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult postUserNotMatchedException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("해당 게시글의 작성자가 아닙니다.");
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult authenticationEntryPointException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("해당 리소스에 접근하기 위한 권한이 없습니다.");
    }

    @ExceptionHandler(CAccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("보유한 권한으로 접근할 수 없는 리소스입니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult accessDeniedException2(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("보유한 권한으로 접근할 수 없는 리소스입니다.");
    }
}
