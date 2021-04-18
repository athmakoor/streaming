package com.streaming.subscription.service.impl;

import com.streaming.exception.ItemNotFoundException;
import com.streaming.service.mapping.ServiceMapper;
import com.streaming.subscription.bean.SubscriptionPack;
import com.streaming.subscription.bean.jpa.SubscriptionPackEntity;
import com.streaming.subscription.repository.SubscriptionPackRepository;
import com.streaming.subscription.service.SubscriptionPackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionPackServiceImpl implements SubscriptionPackService {
    @Resource
    private SubscriptionPackRepository subscriptionPackRepository;
    @Resource
    private ServiceMapper<SubscriptionPack, SubscriptionPackEntity> serviceMapper;

    @Override
    public SubscriptionPack findByProviderAndPackId(String provider, String packId) {
        Optional<SubscriptionPackEntity> subscriptionPackEntityOptional = subscriptionPackRepository.findByProviderAndSku(provider, packId);

        if (!subscriptionPackEntityOptional.isPresent()) {
            throw new ItemNotFoundException("Invalid pack "+ packId + " for provider " + provider);
        }

        return serviceMapper.mapEntityToDTO(subscriptionPackEntityOptional.get(), SubscriptionPack.class);
    }

    @Override
    public List<SubscriptionPack> findByProviderAndOperatorCode(String provider, String operatorCode) {
        List<SubscriptionPack> subscriptionPacks = new ArrayList<>();
        List<SubscriptionPackEntity> subscriptionPackEntities = subscriptionPackRepository.findByProviderAndOperatorCode(provider, operatorCode);

        for (SubscriptionPackEntity subscriptionPackEntity : subscriptionPackEntities) {
            if (!subscriptionPackEntity.getFallbackPack()) {
                subscriptionPacks.add(serviceMapper.mapEntityToDTO(subscriptionPackEntity, SubscriptionPack.class));
            }
        }

        return subscriptionPacks;
    }

    @Override
    public SubscriptionPack findByName(final String provider, final String planName) {
        Optional<SubscriptionPackEntity> subscriptionPackEntity = subscriptionPackRepository.findByProviderAndName(provider, planName);

        if (!subscriptionPackEntity.isPresent()) {
            throw new ItemNotFoundException("Invalid pack name "+ planName + " for provider " + provider);
        }

        return serviceMapper.mapEntityToDTO(subscriptionPackEntity.get(), SubscriptionPack.class);
    }
}
