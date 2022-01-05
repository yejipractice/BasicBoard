package com.example.basicboard.advice;

import com.example.basicboard.advice.exception.CPostNotFoundException;
import com.example.basicboard.advice.exception.CSiginFailedExceoption;
import com.example.basicboard.advice.exception.CUserNotFoundException;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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
        return responseService.getFailResultWithMsg("해당 계정이 존재하지 않습니다.");
    }

    @ExceptionHandler(CPostNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult postNotFoundException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("해당 게시글이 존재하지 않습니다.");
    }

    @ExceptionHandler(CSiginFailedExceoption.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult siginFailedException(HttpServletRequest request, Exception e){
        return responseService.getFailResultWithMsg("계정 정보가 옳지 않습니다.");
    }
}
