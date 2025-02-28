package com.example.CMSR2S.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super("Not found with id: " + id);
    }
}
