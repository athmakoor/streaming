package com.streaming.auth.bean;

public class AuthResponse {
    private Boolean authenticated = false;
    private Boolean otpSent = false;

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Boolean getOtpSent() {
        return otpSent;
    }

    public void setOtpSent(Boolean otpSent) {
        this.otpSent = otpSent;
    }
}
