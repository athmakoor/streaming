package com.streaming.aggregator.timwe.bean.notication;

/**
 * Created by Hari on 1/22/2021.
 */
public class NotificationResponse {
    private NotificationResponseBody partnerNotifResponseBody;
    private String message;
    private Boolean inError;
    private String requestId;
    private String code;

    public NotificationResponseBody getPartnerNotifResponseBody() {
        return partnerNotifResponseBody;
    }

    public void setPartnerNotifResponseBody(final NotificationResponseBody partnerNotifResponseBody) {
        this.partnerNotifResponseBody = partnerNotifResponseBody;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
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

    public Boolean getInError() {
        return inError;
    }

    public void setInError(Boolean inError) {
        this.inError = inError;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" + "partnerNotifResponseBody=" + partnerNotifResponseBody.toString() + ", message='"
                + message + '\'' + ", inError='" + inError + '\'' + ", requestId='" + requestId + '\'' + ", code='"
                + code + '\'' + '}';
    }
}
