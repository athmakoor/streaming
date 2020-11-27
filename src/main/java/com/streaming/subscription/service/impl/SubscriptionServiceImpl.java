package com.streaming.subscription.service.impl;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.annotation.Resource;

import com.streaming.constant.Provider;
import org.springframework.stereotype.Service;

import com.streaming.exception.ConstraintViolationException;
import com.streaming.subscription.SubscriptionUtils;
import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.bean.jpa.SubscriptionEntity;
import com.streaming.subscription.bean.jpa.SubscriptionRequestEntity;
import com.streaming.subscription.repository.SubscriptionRepository;
import com.streaming.subscription.repository.SubscriptionRequestRepository;
import com.streaming.subscription.service.SubscriptionService;
import com.streaming.utils.TimeUtil;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Resource
    private SubscriptionRequestRepository subscriptionRequestRepository;
    @Resource
    private SubscriptionRepository subscriptionRepository;

    @Override
    public GenerateOTPResponse generateOtp(final GenerateOTPRequest data) {
        List<SubscriptionRequestEntity> subscriptionRequestEntities = subscriptionRequestRepository.findByClickId(data.getClickId());

        if (!subscriptionRequestEntities.isEmpty()) {
            throw new ConstraintViolationException("Duplicate click id");
        }

        updatePack(data);
        GenerateOTPResponse response = SubscriptionUtils.generateOTP(data);

        SubscriptionRequestEntity entity = new SubscriptionRequestEntity();

        entity.setMsisdn(data.getMsisdn());
        entity.setResponseMessage(response.toString());
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setRegenerate(false);
        entity.setClickId(data.getClickId());

        subscriptionRequestRepository.save(entity);
        return response;
    }

    private void updatePack(GenerateOTPRequest data) {
        ZonedDateTime date = TimeUtil.getCurrentUTCTime().truncatedTo(ChronoUnit.DAYS);
        List<SubscriptionEntity> entities = subscriptionRepository.findByCreatedAtAfter(date);

        if (data.getProvider().equals(Provider.ZAIN_KUWAIT)) {
            if (entities.size() > 30) {
                data.setPackPrice("100");
                data.setPackValidity("1");
            } else {
                data.setPackPrice("600");
                data.setPackValidity("7");
            }
        }
    }

    @Override
    public VerifyOTPResponse verifyOtp(final VerifyOTPRequest data) {
        VerifyOTPResponse response = SubscriptionUtils.verifyOtp(data);

        SubscriptionEntity entity = new SubscriptionEntity();

        entity.setResponseMessage(response.toString());
        entity.setTransactionId(data.getTransactionId());
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());

        subscriptionRepository.save(entity);

        return response;
    }

    @Override
    public GenerateOTPResponse regenerateOTP(GenerateOTPRequest data) {
        updatePack(data);
        GenerateOTPResponse response = SubscriptionUtils.regenerateOTP(data);

        SubscriptionRequestEntity entity = new SubscriptionRequestEntity();

        entity.setMsisdn(data.getMsisdn());
        entity.setResponseMessage(response.toString());
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());
        entity.setRegenerate(true);

        subscriptionRequestRepository.save(entity);
        return response;
    }
}
