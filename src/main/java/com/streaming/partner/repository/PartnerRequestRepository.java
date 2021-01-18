package com.streaming.partner.repository;

import com.streaming.partner.bean.jpa.PartnerRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRequestRepository extends PagingAndSortingRepository<PartnerRequestEntity, Integer> {
    List<PartnerRequestEntity> findByPartnerTransactionId(String partnerTransactionId);

    List<PartnerRequestEntity> findByClickId(String clickId);
}
