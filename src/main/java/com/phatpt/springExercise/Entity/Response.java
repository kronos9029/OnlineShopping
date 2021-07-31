package com.phatpt.springExercise.entity;

public class Response {
    
    private String errorCode;
	private String successCode;
	private Object data;

    public Response() {
    }

    public Response(String errorCode, String successCode, Object data) {
        this.errorCode = errorCode;
        this.successCode = successCode;
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getSuccessCode() {
        return successCode;
    }
    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    
}
