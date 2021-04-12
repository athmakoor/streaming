package com.streaming.subscription.service.impl;

import com.streaming.service.mapping.ServiceMapper;
import com.streaming.subscription.bean.SubscriptionRequest;
import com.streaming.subscription.bean.jpa.SubscriptionRequestEntity;
import com.streaming.subscription.repository.SubscriptionRequestRepository;
import com.streaming.subscription.service.SubscriptionRequestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class SubscriptionRequestServiceImpl implements SubscriptionRequestService {
    @Resource
    private SubscriptionRequestRepository subscriptionRequestRepository;
    @Resource
    private ServiceMapper<SubscriptionRequest, SubscriptionRequestEntity> serviceMapper;

    @Override
    public SubscriptionRequest findLatestByMsisdn(String msisdn) {
        Optional<SubscriptionRequestEntity> subscriptionRequestEntityOptional = subscriptionRequestRepository.findFirstByMsisdnOrderByIdDesc(msisdn);

        return subscriptionRequestEntityOptional.map(subscriptionRequestEntity -> serviceMapper.mapEntityToDTO(subscriptionRequestEntity, SubscriptionRequest.class)).orElse(null);
    }

    @Override
    public SubscriptionRequest findByMsisdnAndCorrelatedId(final String msisdn, final String correlatedId) {
        return null;
    }

    @Override
    public SubscriptionRequest findLatestBySubscriptionContractId(String contractId) {
        Optional<SubscriptionRequestEntity> subscriptionRequestEntityOptional = subscriptionRequestRepository.findFirstBySubscriptionContractIdOrderByIdDesc(contractId);

        return subscriptionRequestEntityOptional.map(subscriptionRequestEntity -> serviceMapper.mapEntityToDTO(subscriptionRequestEntity, SubscriptionRequest.class)).orElse(null);

    }
}
