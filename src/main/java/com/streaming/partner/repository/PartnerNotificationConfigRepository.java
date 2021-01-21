package com.streaming.partner.repository;

import com.streaming.partner.bean.jpa.PartnerNotificationConfigEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PartnerNotificationConfigRepository extends PagingAndSortingRepository<PartnerNotificationConfigEntity, Integer> {
    Optional<PartnerNotificationConfigEntity> findByPartnerId(String partnerId);
}
