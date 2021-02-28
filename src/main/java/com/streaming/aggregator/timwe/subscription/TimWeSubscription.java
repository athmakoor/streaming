package com.streaming.aggregator.timwe.subscription;

import com.gamesvas.aggregator.timwe.bean.*;
import com.gamesvas.aggregator.timwe.bean.notication.NotificationUserOptinRequest;
import com.gamesvas.aggregator.timwe.bean.notication.SubscriptionUpdateModel;
import com.gamesvas.subscription.bean.jpa.NotificationEntity;
import com.gamesvas.subscription.bean.jpa.SubscriptionEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface TimWeSubscription {
    MtResponse sendMessage(String message, String context);

    String getCgUrl(CgUrlRequest cgUrlRequest, String correlatedId, HttpServletRequest request)
            throws UnsupportedEncodingException;

    UnSubscribeResponse unsubscribe(UnSubscribeRequest unSubscribeRequest);

    TimWeSubscriptionRequest getSubscription(String correlatedId, CgUrlRequest request);

    TimWeSubscriptionRequest getSubscription(String correlatedId, NotificationUserOptinRequest request);

    void updateSubscription(SubscriptionUpdateModel update, NotificationEntity entity);

    SubscriptionStatus getSubStatus(String msisdn);

    String getMessage(SubscriptionEntity entity, String type);
}
