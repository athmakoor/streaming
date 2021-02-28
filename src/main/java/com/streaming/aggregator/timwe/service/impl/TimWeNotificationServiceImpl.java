package com.streaming.aggregator.timwe.service.impl;

import com.gamesvas.aggregator.timwe.bean.MtResponse;
import com.gamesvas.aggregator.timwe.bean.SubscriptionStatus;
import com.gamesvas.aggregator.timwe.bean.TimWeSubscriptionRequest;
import com.gamesvas.aggregator.timwe.bean.notication.*;
import com.gamesvas.aggregator.timwe.constants.MNODeliveryCodes;
import com.gamesvas.aggregator.timwe.constants.MtContext;
import com.gamesvas.aggregator.timwe.constants.NotificationTypes;
import com.gamesvas.aggregator.timwe.constants.SubscriptionStatusCodes;
import com.gamesvas.aggregator.timwe.service.TimWeNotificationService;
import com.gamesvas.aggregator.timwe.subscription.TSubscription;
import com.gamesvas.constant.Provider;
import com.gamesvas.subscription.bean.SubscriptionRequest;
import com.gamesvas.subscription.bean.jpa.NotificationEntity;
import com.gamesvas.subscription.bean.jpa.SubscriptionEntity;
import com.gamesvas.subscription.bean.jpa.SubscriptionPackEntity;
import com.gamesvas.subscription.bean.jpa.SubscriptionRequestEntity;
import com.gamesvas.subscription.repository.NotificationsRepository;
import com.gamesvas.subscription.repository.SubscriptionPackRepository;
import com.gamesvas.subscription.repository.SubscriptionRepository;
import com.gamesvas.subscription.repository.SubscriptionRequestRepository;
import com.gamesvas.subscription.service.SubscriptionRequestService;
import com.gamesvas.utils.TimeUtil;
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

        SubscriptionUpdateModel updateModel = new SubscriptionUpdateModel();
        updateModel.setMsisdn(data.getMsisdn());

        if (packEntity.isPresent()) {
            updateModel.setPackId(data.getPricepointId().toString());
        }

        if (subType.equals(NotificationTypes.OPT_IN)) {
            entity.setSyncType(NotificationTypes.OPT_IN);

            updateModel.setClickId(correlatedId);
            updateModel.setUpdateType(NotificationTypes.OPT_IN);
            updateModel.setStatus(SubscriptionStatusCodes.PARKING);
        } else if(subType.equals(NotificationTypes.RE_NEWED)) {
            entity.setSyncType(NotificationTypes.RE_NEWED);

            updateModel.setClickId(correlatedId);
            updateModel.setUpdateType(NotificationTypes.RE_NEWED);
            updateModel.setStatus(SubscriptionStatusCodes.RENEWAL);
        }

        try {
            if (data.getMnoDeliveryCode().equals(MNODeliveryCodes.SUCCESS)) {
                tSubscription.updateSubscription(updateModel, entity);
                notificationsRepository.save(entity);
                notificationResponseBody.setCorrelationId(correlatedId);
                notificationResponse.setInError(false);
                notificationResponse.setCode("SUCCESS");
                notificationResponse.setMessage("Subcription request successfully proccessed");
                notificationResponse.setRequestId(generateType1UUID().toString());
                notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
                notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
            } else {
                notificationsRepository.save(entity);
                notificationResponseBody.setCorrelationId(correlatedId);
                notificationResponse.setInError(false);
                notificationResponse.setCode("SUCCESS");
                notificationResponse.setMessage("Mno Delivery failed");
                notificationResponse.setRequestId(generateType1UUID().toString());
                notificationResponseBody.setTransactionUUID(data.getTransactionUUID());
                notificationResponse.setPartnerNotifResponseBody(notificationResponseBody);
            }
        } catch (Exception ex) {
            LOGGER.error("Timwe OPT_IN Exception:" + data.getMsisdn() , ex.getMessage());
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
    public NotificationResponse save(HttpServletRequest request, NotificationUserOptoutRequest data, String provider, String subType) {
        NotificationEntity entity = new NotificationEntity();
        NotificationResponse notificationResponse = new NotificationResponse();
        NotificationResponseBody notificationResponseBody = new NotificationResponseBody();
        String correlatedId = "";

        SubscriptionStatus subscriptionStatus = tSubscription.getSubStatus(data.getMsisdn());

        String state = subscriptionStatus.getStatusCode();

        if ((state.equals(SubscriptionStatusCodes.PARKING) || state.equals(SubscriptionStatusCodes.SUBSCRIBE))) {
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
            updateModel.setPackId(data.getPricepointId().toString());
            updateModel.setClickId(correlatedId);
            updateModel.setUpdateType(NotificationTypes.OPT_OUT);

            try {
                updateModel.setStatus(SubscriptionStatusCodes.UNSUBSCRIBE);

                tSubscription.updateSubscription(updateModel, entity);

                if (!data.equals("OPTOUT_NO_SUB") && !data.equals("OPTOUT_MISSING_PARAM")) {
                    Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnOrderByIdDesc(data.getMsisdn());
                    String message = tSubscription.getMessage(existing.get(), NotificationTypes.OPT_OUT);

                    MtResponse mtResponse = tSubscription.sendMessage(message, MtContext.SUBSCRIPTION);
                    LOGGER.info("Timwe Mt Message response" , mtResponse.toString());
                }

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

                LOGGER.error("Timwe OPT_OUT Exception:" + data.getMsisdn(), ex.getMessage());
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

        if ("SUCCESS".equals(data.getMnoDeliveryCode())) {
            updateModel.setStatus("subscribe");
            updateModel.setTotalCharged(data.getTotalCharged());
            updateModel.setUpdateType(NotificationTypes.FIRST_CHARGE);
            tSubscription.updateSubscription(updateModel, entity);


            Optional<SubscriptionEntity> existing = subscriptionRepository.findFirstByMsisdnOrderByIdDesc(data.getMsisdn());
            String message = tSubscription.getMessage(existing.get(), NotificationTypes.FIRST_CHARGE);

            MtResponse mtResponse = tSubscription.sendMessage(message, MtContext.SUBSCRIPTION);
            LOGGER.info("Timwe Mt Message response", mtResponse.toString());

            notificationResponse.setCode("SUCCESS");
            notificationResponse.setMessage("Sucessfully Charged the subscription");
        } else {
            notificationResponse.setCode("SUCCESS");
            notificationResponse.setMessage("No Changes in subscription charge");
        }

        notificationsRepository.save(entity);

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
