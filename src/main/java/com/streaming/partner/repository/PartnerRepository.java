package com.streaming.partner.repository;

import com.streaming.partner.bean.jpa.PartnerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends PagingAndSortingRepository<PartnerEntity, Integer> {
    Optional<PartnerEntity> findByPartnerId(String partnerId);
}
