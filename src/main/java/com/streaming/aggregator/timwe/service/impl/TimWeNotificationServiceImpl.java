package com.streaming.aggregator.timwe.service.impl;

import com.streaming.aggregator.timwe.bean.MtResponse;
import com.streaming.aggregator.timwe.bean.TimWeSubscriptionRequest;
import com.streaming.aggregator.timwe.bean.notication.*;
import com.streaming.aggregator.timwe.constants.MNODeliveryCodes;
import com.streaming.aggregator.timwe.constants.MtContext;
import com.streaming.aggregator.timwe.service.TimWeNotificationService;
import com.streaming.aggregator.timwe.subscription.TSubscription;
import com.streaming.constant.Provider;
import com.streaming.subscription.bean.NotificationTypes;
import com.streaming.subscription.bean.SubscriptionRequest;
import com.streaming.subscription.bean.SubscriptionStatus;
import com.streaming.subscription.bean.jpa.NotificationEntity;
import com.streaming.subscription.bean.jpa.SubscriptionEntity;
import com.streaming.subscription.bean.jpa.SubscriptionPackEntity;
import com.streaming.subscription.bean.jpa.SubscriptionRequestEntity;
import com.streaming.subscription.constants.SubscriptionStatusCodes;
import com.streaming.subscription.repository.NotificationsRepository;
import com.streaming.subscription.repository.SubscriptionPackRepository;
import com.streaming.subscription.repository.SubscriptionRepository;
import com.streaming.subscription.repository.SubscriptionRequestRepository;
import com.streaming.subscription.service.SubscriptionRequestService;
import com.streaming.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Hari on 1/29/2021.
 */
@Service
public class TimWeNotificationServiceImpl implements TimWeNotificationService {
    @Resource
    private NotificationsRepository notificationsRepository;

    @Resource
    private SubscriptionPackRepository subscriptionPackRepository;

    @Resource
    private SubscriptionRequestService subscriptionRequestService;

    @Resource
    private SubscriptionRequestRepository subscriptionRequestRepository;

    @Resource
    private SubscriptionRepository subscriptionRepository;

    @Resource
    private TSubscription tSubscription;

    private static final Logger LOGGER = LoggerFactory.getLogger(TSubscription.class);

    @Override
    public NotificationResponse save(HttpServletRequest request, NotificationUserOptinRequest data, String provider, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();
        String correlatedId = "";

        SubscriptionRequest subscriptionRequest = subscriptionRequestService.findLatestByMsisdn(data.getMsisdn());
        SubscriptionStatus subscriptionStatus = tSubscription.getSubStatus(data.getMsisdn());
        Optional<SubscriptionPackEntity> packEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, data.getPricepointId().toString());

        String state = subscriptionStatus.getStatusCode();

        if (state.equals(SubscriptionStatusCodes.NONE)) {
            if (data.getTrackingId() == null) {
                correlatedId = new Date().getTime() + "_" + data.getMsisdn();
            } else {
                correlatedId = data.getTrackingId();
            }

            if (subscriptionRequest == null) {
                addSubscriptionRequestData(correlatedId, data);
            } else {
                correlatedId = subscriptionStatus.getCorrelatedId();
            }
        } else {
            correlatedId = subscriptionStatus.getCorrelatedId();
        }

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(data.toString());
        entity.setType("notification");
        entity.setClickId(correlatedId);
        entity.setMsisdn(data.getMsisdn());
        entity.setProvider(Provider.TIMWE);
        entity.setEntryChannel(data.getEntryChannel());
        entity.setTotalCharged(data.getTotalCharged());

        SubscriptionUpdateModel updateModel = new SubscriptionUpdateModel();
        updateModel.setMsisdn(data.getMsisdn());

        if (packEntity.isPresent()) {
            updateModel.setPackId(data.getPricepointId().toString());
        }

        try {
            entity.setSyncType(NotificationTypes.OPT_IN);

            updateModel.setClickId(correlatedId);
            updateModel.setUpdateType(NotificationTypes.OPT_IN);
            updateModel.setStatus(SubscriptionStatusCodes.PARKING);

            tSubscription.updateSubscription(updateModel, entity);
            notificationsRepository.save(entity);

            notificationResponseBody.setCorrelationId(correlatedId);
            notificationResponse.setRequestId(generateType1UUID().toString());
            notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
            notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
        } catch (Exception ex) {
            LOGGER.error("Timwe Subscribe Exception:" + data.getMsisdn(), ex.getMessage());
            notificationResponseBody.setCorrelationId(correlatedId);
            notificationResponse.setInError(true);
            notificationResponse.setCode("FAILED");
            notificationResponse.setMessage("Failed to update subscription");
            notificationResponse.setRequestId(generateType1UUID().toString());
            notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
            notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
        }

        return notificationResponse;
    }

    @Override
    public NotificationResponse save(HttpServletRequest request, NotificationRenewalRequest data, String provide, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();
        String correlatedId = "";

        SubscriptionStatus subscriptionStatus = tSubscription.getSubStatus(data.getMsisdn());
        Optional<SubscriptionPackEntity> packEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, String.valueOf(data.getPricepointId()));

        String state = subscriptionStatus.getStatusCode();

        if (state.equals(SubscriptionStatusCodes.NONE)) {
            if (data.getTrackingId() == null && subscriptionStatus.getCorrelatedId().isEmpty()) {
                correlatedId = new Date().getTime() + "_" + data.getMsisdn();
            } else {
                correlatedId = subscriptionStatus.getCorrelatedId();
            }
        } else {
            correlatedId = subscriptionStatus.getCorrelatedId();
        }

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(data.toString());
        entity.setType("notification");
        entity.setClickId(correlatedId);
        entity.setMsisdn(data.getMsisdn());
        entity.setProvider(Provider.TIMWE);
        entity.setEntryChannel(data.getEntryChannel());
        entity.setTotalCharged(data.getTotalCharged());

        SubscriptionUpdateModel updateModel = new SubscriptionUpdateModel();
        updateModel.setMsisdn(data.getMsisdn());

        if (packEntity.isPresent()) {
            updateModel.setPackId(data.getPricepointId().toString());
        }

        try {
            if (data.getMnoDeliveryCode().equals(MNODeliveryCodes.DELIVERED)) {
                entity.setSyncType(NotificationTypes.RE_NEWED);

                updateModel.setClickId(correlatedId);
                updateModel.setUpdateType(NotificationTypes.RE_NEWED);
                updateModel.setStatus(SubscriptionStatusCodes.RENEWAL);
                updateModel.setPackId(packEntity.get().getSku());

                tSubscription.updateSubscription(updateModel, entity);
                notificationsRepository.save(entity);

                notificationResponseBody.setCorrelationId(correlatedId);

                try {
                    Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnOrderByIdDesc(data.getMsisdn());
                    String message = tSubscription.getMessage(existing.get(), NotificationTypes.RE_NEWED, packEntity.get());

                    MtResponse mtResponse = tSubscription.sendMessage(message, MtContext.RENEW, existing.get().getPackId(), existing.get().getMsisdn());
                    LOGGER.info("Timwe Mt Message response", mtResponse.toString());

                    notificationResponse.setInError(false);
                    notificationResponse.setCode("SUCCESS");
                    notificationResponse.setMessage("Subcription request successfully proccessed");
                } catch (Exception ex) {
                    LOGGER.info("Timwe Mt Exception", ex.getMessage());
                    notificationResponse.setCode("FAILED");
                    notificationResponse.setInError(true);
                    notificationResponse.setMessage("Failed to send Mt message " + ex.getMessage());
                }


                notificationResponse.setRequestId(generateType1UUID().toString());
                notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
                notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
            } else {
                notificationsRepository.save(entity);
                notificationResponseBody.setCorrelationId(correlatedId);
                notificationResponse.setInError(false);
                notificationResponse.setCode("SUCCESS");
                notificationResponse.setMessage("NO BALANCE");
                notificationResponse.setRequestId(generateType1UUID().toString());
                notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
                notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
            }
        } catch (Exception ex) {
            LOGGER.error("Timwe Subscribe Exception:" + data.getMsisdn() , ex.getMessage());
            notificationResponseBody.setCorrelationId(correlatedId);
            notificationResponse.setInError(true);
            notificationResponse.setCode("FAILED");
            notificationResponse.setMessage("Failed to update subscription.");
            notificationResponse.setRequestId(generateType1UUID().toString());
            notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
            notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
        }

        return notificationResponse;
    }

    @Override
    public NotificationResponse save(HttpServletRequest request, NotificationUserOptoutRequest data, String provider, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();
        String correlatedId = "";

        SubscriptionStatus subscriptionStatus = tSubscription.getSubStatus(data.getMsisdn());

        String state = subscriptionStatus.getStatusCode();

        if ((state.equals(SubscriptionStatusCodes.PARKING) || state.equals(SubscriptionStatusCodes.RENEWAL)
                || state.equals(SubscriptionStatusCodes.SUBSCRIBE))) {
            correlatedId = subscriptionStatus.getCorrelatedId();
            entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
            entity.setResponseMessage(data.toString());
            entity.setSyncType(NotificationTypes.OPT_OUT);
            entity.setType("notification");
            entity.setClickId(correlatedId);
            entity.setMsisdn(data.getMsisdn());
            entity.setProvider(Provider.TIMWE);

            SubscriptionUpdateModel updateModel = new SubscriptionUpdateModel();

            updateModel.setMsisdn(data.getMsisdn());
            updateModel.setClickId(correlatedId);
            updateModel.setUpdateType(NotificationTypes.OPT_OUT);

            try {
                updateModel.setStatus(SubscriptionStatusCodes.UNSUBSCRIBE);

                tSubscription.updateSubscription(updateModel, entity);
                notificationsRepository.save(entity);

                notificationResponseBody.setCorrelationId(correlatedId);
                notificationResponse.setInError(false);
                notificationResponse.setCode("SUCCESS");
                notificationResponse.setMessage("Sucessfully UnSubscribed");
                notificationResponse.setRequestId(generateType1UUID().toString());
                notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
            } catch (Exception ex) {
                notificationsRepository.save(entity);

                notificationResponseBody.setCorrelationId(correlatedId);
                notificationResponse.setInError(true);
                notificationResponse.setCode("INTERNAL_ERROR");
                notificationResponse.setMessage("Failed to UnSubscribe");
                notificationResponse.setRequestId(generateType1UUID().toString());
                notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);

                LOGGER.error("Timwe unsub Exception:" + data.getMsisdn(), ex.getMessage());
            }
        } else {
            entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
            entity.setResponseMessage(data.toString());
            entity.setSyncType(NotificationTypes.OPT_OUT);
            entity.setType("notification");
            entity.setClickId("");
            entity.setMsisdn(data.getMsisdn());
            entity.setProvider(Provider.TIMWE);
            entity.setEntryChannel(data.getEntryChannel());

            notificationResponseBody.setCorrelationId("");
            notificationResponse.setInError(true);
            notificationResponse.setCode("UNKNOWN_MSISDN");
            notificationResponse.setMessage("There is no active subscription to the mobile number");
            notificationResponse.setRequestId(generateType1UUID().toString());
            notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);

            LOGGER.error("Timwe OPT_OUT Exception:" + data.getMsisdn(), "No active subscription");
        }

        return notificationResponse;
    }

    @Override
    public NotificationResponse save(HttpServletRequest request, NotificationDNRequest data, String provide, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();

        String correlatedId;

        if (data.getTrackingId() != null) {
            correlatedId = data.getTrackingId();
        } else {
            correlatedId = new Date().getTime() + "_" + data.getUserIdentifier();
        }

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(data.toString());
        entity.setSyncType(NotificationTypes.MOBILE_TERMINATE);
        entity.setType("notification");
        entity.setClickId(correlatedId);
        entity.setMsisdn(data.getUserIdentifier());
        entity.setProvider(Provider.TIMWE);

        notificationsRepository.save(entity);

        notificationResponseBody.setCorrelationId(correlatedId);
        notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
        notificationResponse.setInError(false);
        notificationResponse.setCode("SUCCESS");
        notificationResponse.setMessage("Sucessfully UnSubscribed");
        notificationResponse.setRequestId(generateType1UUID().toString());

        return notificationResponse;
    }

    @Override
    public NotificationResponse save(HttpServletRequest request, NotificationMoRequest data, String provide, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();

        String correlatedId;

        if (data.getTrackingId() != null) {
            correlatedId = data.getTrackingId();
        } else {
            correlatedId = new Date().getTime() + "_" + data.getMsisdn();
        }

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(data.toString());
        entity.setSyncType(NotificationTypes.MOBILE_ORIGIN);
        entity.setType("notification");
        entity.setClickId(correlatedId);
        entity.setMsisdn(data.getMsisdn());
        entity.setProvider(Provider.TIMWE);

        notificationsRepository.save(entity);

        notificationResponseBody.setCorrelationId(correlatedId);
        notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
        notificationResponse.setInError(false);
        notificationResponse.setCode("SUCCESS");
        notificationResponse.setMessage("");
        notificationResponse.setRequestId(generateType1UUID().toString());

        return notificationResponse;
    }

    @Override
    public NotificationResponse save(HttpServletRequest request, NotificationChargeRequest data, String provide, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();

        SubscriptionStatus subscriptionStatus = tSubscription.getSubStatus(data.getMsisdn());
        String state = subscriptionStatus.getStatusCode();
        String correlatedId;

        if (state.equals(SubscriptionStatusCodes.NONE)) {
            if (data.getTrackingId() == null) {
                correlatedId = new Date().getTime() + "_" + data.getMsisdn();
            } else {
                correlatedId = data.getTrackingId();
            }
        } else {
            correlatedId = subscriptionStatus.getCorrelatedId();
        }

        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setResponseMessage(data.toString());
        entity.setType("notification");
        entity.setClickId(correlatedId);
        entity.setMsisdn(data.getMsisdn());
        entity.setProvider(Provider.TIMWE);
        entity.setEntryChannel(data.getEntryChannel());

        SubscriptionUpdateModel updateModel = new SubscriptionUpdateModel();
        updateModel.setMsisdn(data.getMsisdn());

         entity.setSyncType(NotificationTypes.FIRST_CHARGE);
         updateModel.setClickId(correlatedId);

         Optional<SubscriptionPackEntity> packEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, data.getPricepointId().toString());

         if (packEntity.isPresent()) {
             updateModel.setPackId(data.getPricepointId().toString());
         }

        if ("DELIVERED".equals(data.getMnoDeliveryCode())) {
            entity.setTotalCharged(data.getTotalCharged());

            updateModel.setStatus("subscribe");
            updateModel.setTotalCharged(data.getTotalCharged());
            updateModel.setUpdateType(NotificationTypes.FIRST_CHARGE);
            tSubscription.updateSubscription(updateModel, entity);

            notificationResponse.setCode("SUCCESS");
            notificationResponse.setMessage("Sucessfully Charged the subscription");

            notificationsRepository.save(entity);

            try {
                Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnOrderByIdDesc(data.getMsisdn());
                String message = tSubscription.getMessage(existing.get(), NotificationTypes.FIRST_CHARGE, packEntity.get());

                MtResponse mtResponse = tSubscription.sendMessage(message, MtContext.STATE_LESS, existing.get().getPackId(), existing.get().getMsisdn());
                LOGGER.info("Timwe Mt Message response", mtResponse.toString());

            } catch (Exception ex) {
                notificationResponse.setCode("FAILED");
                notificationResponse.setMessage("Failed to send Mt message" + ex.getMessage());
            }
        } else {
            notificationResponse.setCode("SUCCESS");
            notificationResponse.setMessage("No Changes in subscription charge");
            notificationsRepository.save(entity);
        }

        notificationResponseBody.setCorrelationId(correlatedId);
        notificationResponse.setInError(false);
        notificationResponse.setRequestId(generateType1UUID().toString());
        notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
        notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);

        return notificationResponse;
    }


    private static long get64LeastSignificantBitsForVersion1() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong + variant3BitFlag;
    }

    private static long get64MostSignificantBitsForVersion1() {
        LocalDateTime start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
        Duration duration = Duration.between(start, LocalDateTime.now());
        long seconds = duration.getSeconds();
        long nanos = duration.getNano();
        long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
        long least12SignificatBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
        long version = 1 << 12;
        return
                (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificatBitOfTime;
    }

    public static UUID generateType1UUID() {
        long most64SigBits = get64MostSignificantBitsForVersion1();
        long least64SigBits = get64LeastSignificantBitsForVersion1();

        return new UUID(most64SigBits, least64SigBits);
    }

    private void addSubscriptionRequestData(String correlatedId, NotificationUserOptinRequest request) {
        TimWeSubscriptionRequest subscriptionRequest = tSubscription.getSubscription(correlatedId, request);

        Optional<SubscriptionPackEntity> packEntity = subscriptionPackRepository.findByProviderAndSku(Provider.TIMWE, subscriptionRequest.getPackId());

        if(packEntity.isPresent()) {
            subscriptionRequest.setPricepointId(packEntity.get().getSku());
            subscriptionRequest.setCurrency(packEntity.get().getCurrency());
            subscriptionRequest.setPrice(packEntity.get().getPrice());
        }

        subscriptionRequest.setMsisdn(request.getMsisdn());
        addSubscriptionRequest(subscriptionRequest);
    }

    private void addSubscriptionRequest(TimWeSubscriptionRequest request) {
        SubscriptionRequestEntity entity = new SubscriptionRequestEntity();

        entity.setClickId(request.getCorrelatorId());
        entity.setMsisdn(request.getMsisdn());
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setPrice(request.getPrice());
        entity.setRequestBody(request.toString());

        subscriptionRequestRepository.save(entity);
    }
}
