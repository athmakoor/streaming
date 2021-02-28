package com.streaming.aggregator.timwe.bean;

/**
 * Created by Hari on 1/22/2021.
 */
public class MtResponse {
    private MtResponseBody responseData;

    private String message;

    private Boolean inError;

    private String requestId;

    private String code;

    public MtResponseBody getResponseData() {
        return responseData;
    }

    public void setResponseData(final MtResponseBody responseData) {
        this.responseData = responseData;
    }

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

    @Override
    public String toString() {
        return "MtResponse{" + "responseData=" + responseData.toString() + ", message='" + message + '\'' + ", inError=" + inError
                + ", requestId='" + requestId + '\'' + ", code='" + code + '\'' + '}';
    }
}
