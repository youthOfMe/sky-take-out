package com.chenhai.exception;

/**
 * 作者：洋哥
 * 描述：牛逼
 */
public class AccountExisted extends BaseException {
    public AccountExisted() {
    }

    public AccountExisted(String msg) {
        super(msg);
    }
}
