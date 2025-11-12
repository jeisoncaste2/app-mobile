package com.app.backend.dto;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
    }

    public String MessageResponse(String message) {
        this.message = message;
    }

    public void getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}