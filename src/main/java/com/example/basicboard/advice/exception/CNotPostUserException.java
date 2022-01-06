package com.example.basicboard.advice.exception;

public class CNotPostUserException extends RuntimeException{
    public CNotPostUserException(String msg, Throwable t){
        super(msg, t);
    }

    public CNotPostUserException(String msg){
        super(msg);
    }

    public CNotPostUserException(){
        super();
    }
}
