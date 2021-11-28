package com.example.exchangegif.exceptions;

public class DefaultException extends RuntimeException{
    public DefaultException() {
    }

    public DefaultException(String message) {
        super(message);
    }
}
