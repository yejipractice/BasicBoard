package com.example.basicboard.advice.exception;

public class CSiginFailedExceoption extends RuntimeException{
    public CSiginFailedExceoption(String msg, Throwable t){
        super(msg, t);
    }
    public CSiginFailedExceoption(String msg){
        super(msg);
    }

    public CSiginFailedExceoption() {
        super();
    }
}
