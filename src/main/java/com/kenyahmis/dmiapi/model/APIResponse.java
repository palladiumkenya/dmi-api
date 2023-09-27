package com.kenyahmis.dmiapi.model;

public class APIResponse<T> {
    private T data;
    private String message;

    public APIResponse(T t, String message) {
        this.data = t;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
