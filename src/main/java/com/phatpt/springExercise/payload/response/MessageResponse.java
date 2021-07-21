package com.phatpt.springExercise.payload.response;

public class messageResponse {
    private String message;

    public messageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
