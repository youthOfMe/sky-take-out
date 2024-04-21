package com.chenhai.exception;
public class PasswordNotFoundException extends BaseException {
    public PasswordNotFoundException() {
    }

    public PasswordNotFoundException(String msg) {
        super(msg);
    }
}
