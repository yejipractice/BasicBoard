package com.example.basicboard.advice.exception;

public class CAdminTokenException extends RuntimeException{
    public CAdminTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public CAdminTokenException(String msg) {
        super(msg);
    }

    public CAdminTokenException() {
        super();
    }
}
