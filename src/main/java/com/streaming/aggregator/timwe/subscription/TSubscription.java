package com.streaming.aggregator.timwe.subscription;

import com.streaming.aggregator.timwe.bean.*;
import com.streaming.aggregator.timwe.bean.notication.NotificationUserOptinRequest;
import com.streaming.aggregator.timwe.bean.notication.SubscriptionUpdateModel;
import com.streaming.aggregator.timwe.service.TimWeSubscriptionService;
import com.streaming.properties.PropertyManager;
import com.streaming.subscription.bean.NotificationTypes;
import com.streaming.subscription.bean.SubscriptionStatus;
import com.streaming.subscription.bean.jpa.NotificationEntity;
import com.streaming.subscription.bean.jpa.SubscriptionEntity;
import com.streaming.subscription.bean.jpa.SubscriptionPackEntity;
import com.streaming.subscription.service.SubscriptionRequestService;
import com.streaming.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HARIKRISHNA on 1/20/2021.
 */
@Service
public class TSubscription implements TimWeSubscription {
    private static final Logger LOGGER = LoggerFactory.getLogger(TSubscription.class);
    private static final Map<String, String> errorMessages = new HashMap<>();

    @Resource
    private TimWeSubscriptionService subscriptionService;

    @Resource
    private SubscriptionRequestService subscriptionRequestService;

    @Override
    public MtResponse sendMessage(final String message, final String context, final String pricepointId, final String msisdn) {
        MtResponse response;
        MtRequest mtRequest = new MtRequest();

        String mtUrl = PropertyManager.getPropValue("timwe.mtMessage");

        mtRequest.setLargeAccount(PropertyManager.getPropValue("timwe.largeAccount"));
        mtRequest.setProductId(PropertyManager.getPropValue("timwe.product.soccermania"));
        mtRequest.setMcc(PropertyManager.getPropValue("timwe.mcc"));
        mtRequest.setMnc(PropertyManager.getPropValue("timwe.mnc"));

        ZonedDateTime now = TimeUtil.getCurrentUTCTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");

        String date = now.format(formatter).replace("+0000","Z");

        mtRequest.setSendDate(date);
        mtRequest.setTimeZone("Asia/Bahrain");
        mtRequest.setText(mtRequest.getText());
        mtRequest.setContext(context);
        mtRequest.setText(message);
        mtRequest.setPriority("NORMAL");
        mtRequest.setMsisdn(msisdn);
        mtRequest.setPricepointId(PropertyManager.getPropValue("timwe.free.pricePointId"));

        mtUrl = mtUrl.replace("{partnerRoleId}", PropertyManager.getPropValue("timwe.roleId"));
        mtUrl = mtUrl.replace("{channel}", "sms");

        response = SubscriptionUtils.sendMtMessageRequest(PropertyManager.getPropValue("timwe.mt.apikey"),
                PropertyManager.getPropValue("timwe.serviceId"), PropertyManager.getPropValue("timwe.mt.privatekey"),mtRequest, mtUrl);

        LOGGER.info("TIMWE Mt Message Response:" + response.toString());

        return response;
    }

    @Override
    public String getCgUrl(final CgUrlRequest cgUrlRequest, final String correlatedId, final HttpServletRequest request)
            throws UnsupportedEncodingException {
        String subscribeUrl = PropertyManager.getPropValue("timwe.subscribe");
        HashMap<String, String> vals = new HashMap<>();

        String password = SubscriptionUtils.getAuthenticationHeader(PropertyManager.getPropValue("timwe.serviceId"), PropertyManager.getPropValue("timwe.cg.privatekey"));

        vals.put("{ROLEID}", PropertyManager.getPropValue("timwe.roleId"));
        vals.put("{PASSWORD}", URLEncoder.encode(password, "UTF-8"));
        vals.put("{MSISDN}", cgUrlRequest.getMsisdn());
        vals.put("{COUNTRYID}", cgUrlRequest.getCountryId());
        vals.put("{CHANNEL}", PropertyManager.getPropValue("timwe.entryChannel"));
        vals.put("{PRODUCTID}", PropertyManager.getPropValue("timwe.product.soccermania"));
        vals.put("{CORRELATORID}", correlatedId);
        vals.put("{APIKEY}",  URLEncoder.encode(PropertyManager.getPropValue("timwe.cg.apikey"), "UTF-8"));

        for(HashMap.Entry<String, String> val : vals.entrySet())
        {
            subscribeUrl = subscribeUrl.replace(val.getKey(), val.getValue());
            LOGGER.info("TIMWE CG URL:" + subscribeUrl);
        }

        return subscribeUrl;
    }

    @Override
    public UnSubscribeResponse unsubscribe(final UnSubscribeRequest unSubscribeRequest) {
       UnSubscribeResponse response;
       String unsubUrl = PropertyManager.getPropValue("timwe.unsubscribe");

       unSubscribeRequest.setLargeAccount(PropertyManager.getPropValue("timwe.largeAccount"));
       unSubscribeRequest.setMcc(PropertyManager.getPropValue("timwe.mcc"));
       unSubscribeRequest.setMnc(PropertyManager.getPropValue("timwe.mnc"));
       unSubscribeRequest.setProductId(PropertyManager.getPropValue("timwe.product.soccermania"));
       unSubscribeRequest.setSubKeyword(PropertyManager.getPropValue("timwe.subKeyword"));
       unSubscribeRequest.setEntryChannel(PropertyManager.getPropValue("timwe.entryChannel"));

       String unSubUrl = unsubUrl.replace("{partnerRoleId}", PropertyManager.getPropValue("timwe.roleId"));

       response = SubscriptionUtils.unSubscribeRequest(PropertyManager.getPropValue("timwe.unSub.apikey"),
               PropertyManager.getPropValue("timwe.serviceId"), PropertyManager.getPropValue("timwe.unSub.privatekey"), unSubscribeRequest, unSubUrl);

       LOGGER.info("TIMWE UnSub Response:" + response.toString());

       return response;
    }

    @Override
    public TimWeSubscriptionRequest getSubscription(String correlatedId, CgUrlRequest request) {
        TimWeSubscriptionRequest subscriptionRequest = new TimWeSubscriptionRequest();

        subscriptionRequest.setProductId(PropertyManager.getPropValue("timwe.product.soccermania"));
        subscriptionRequest.setCorrelatorId(correlatedId);
        subscriptionRequest.setPackId(request.getPackId());
        subscriptionRequest.setRoleId(PropertyManager.getPropValue("timwe.roleId"));

        return subscriptionRequest;
    }

    @Override
    public TimWeSubscriptionRequest getSubscription(String correlatedId, NotificationUserOptinRequest request) {
        TimWeSubscriptionRequest subscriptionRequest = new TimWeSubscriptionRequest();

        subscriptionRequest.setProductId(PropertyManager.getPropValue("timwe.product.soccermania"));
        subscriptionRequest.setCorrelatorId(correlatedId);
        subscriptionRequest.setPackId(request.getPricepointId().toString());
        subscriptionRequest.setRoleId(PropertyManager.getPropValue("timwe.roleId"));

        return subscriptionRequest;
    }

    @Override
    public void updateSubscription(SubscriptionUpdateModel update, NotificationEntity entity) {
        String msisdn = update.getMsisdn();
        String clickId = update.getClickId();
        String status =  update.getStatus();
        String packId =  update.getPackId();

        switch (update.getUpdateType()) {
            case NotificationTypes.OPT_IN:
                subscriptionService.save(update.getStatus(), clickId, update.getMsisdn());
                break;

            case  NotificationTypes.OPT_OUT:
                subscriptionService.save(update.getStatus(), clickId, update.getMsisdn());
                break;

            case  NotificationTypes.FIRST_CHARGE:
                subscriptionService.save(status, clickId, update.getMsisdn(), update.getTotalCharged(), packId);
                break;

            case NotificationTypes.RE_NEWED:
                subscriptionService.save(status, clickId, update.getMsisdn(), packId, true);

                break;
        }
    }

    @Override
    public SubscriptionStatus getSubStatus(final String msisdn) {
        return subscriptionService.getSubscriptionStatus(msisdn);
    }

    @Override
    public String getMessage(SubscriptionEntity entity, String type, SubscriptionPackEntity packEntity) {
        String message = null;

        switch (type) {
            case NotificationTypes.OPT_IN:
                message = "Welcome to Scoccer Mania. To login click .To login click http://soccer.mooddit.com?msisdn=" + entity.getMsisdn() + "&provider=timwe";
                break;
            case NotificationTypes.OPT_OUT:
                message = "You have successfully Unsubscribed from Soccer Mania Service on the mobile number " + entity.getMsisdn();
                break;
            case NotificationTypes.FIRST_CHARGE:
                message = "You have successfully subscribed to Soccer Mania Service.Your Account has been charged  with " + packEntity.getChargeAmount() + " " + packEntity.getCurrency() +
                        " for "+ packEntity.getDays() + " day(s) VAT inclusive.To login click http://soccer.mooddit.com?msisdn="  + entity.getMsisdn()+ "&provider=timwe";
                break;
            case NotificationTypes.RE_NEWED:
                message = "Keep enjoying your favorite videos by login click http://soccer.mooddit.com?msisdn="  + entity.getMsisdn()+ "&provider=timwe";
                break;
        }

        return message;
    }
}
