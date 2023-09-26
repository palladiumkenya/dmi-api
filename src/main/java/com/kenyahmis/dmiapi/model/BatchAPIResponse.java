package com.kenyahmis.dmiapi.model;

public class BatchAPIResponse {
    private String message;
    private String refId;

    public BatchAPIResponse(String message, String refId) {
        this.message = message;
        this.refId = refId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
