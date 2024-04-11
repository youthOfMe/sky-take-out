package com.sky.exception;
public class PasswordNotFoundException extends BaseException {
    public PasswordNotFoundException() {
    }

    public PasswordNotFoundException(String msg) {
        super(msg);
    }
}
