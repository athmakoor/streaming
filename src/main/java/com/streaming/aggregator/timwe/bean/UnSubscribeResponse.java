package com.streaming.aggregator.timwe.bean;

public class UnSubscribeResponse {
    private String message;
    private Boolean inError;
    private String requestId;
    private String code;
    private SubscriptionResponseBody responseData;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Boolean getInError() {
        return inError;
    }

    public void setInError(final Boolean inError) {
        this.inError = inError;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(final String requestId) {
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public SubscriptionResponseBody getResponseData() {
        return responseData;
    }

    public void setResponseData(final SubscriptionResponseBody responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return "UnSubscribeResponse{" + "message='" + message + '\'' + ", inError=" + inError + ", requestId='"
                + requestId + '\'' + ", code='" + code + '\'' + ", responseData=" + responseData.toString() + '}';
    }
}
