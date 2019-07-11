package com.app.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyException extends RuntimeException {

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
