package com.streaming.partner.repository;

import com.streaming.partner.bean.jpa.PartnerRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartnerRequestRepository extends PagingAndSortingRepository<PartnerRequestEntity, Integer> {
}
