package com.example.exchangegif.responses;

public class DefaultErrorResponse {
    private final String message;

    public DefaultErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
