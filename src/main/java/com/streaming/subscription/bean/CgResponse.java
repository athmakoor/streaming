package com.streaming.subscription.bean;

public class CgResponse {
    private String error_code;
    private String errorDesc;
    private String cgId;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getCgId() {
        return cgId;
    }

    public void setCgId(String cgId) {
        this.cgId = cgId;
    }
}
