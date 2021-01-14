package com.streaming.partner.service.impl;

import com.streaming.partner.bean.PartnerRequest;
import com.streaming.partner.bean.jpa.PartnerRequestEntity;
import com.streaming.partner.repository.PartnerRequestRepository;
import com.streaming.partner.service.PartnerRequestService;
import com.streaming.service.mapping.ServiceMapper;
import com.streaming.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class PartnerRequestServiceImpl implements PartnerRequestService {
    @Resource
    private PartnerRequestRepository partnerRequestRepository;
    @Resource
    private ServiceMapper<PartnerRequest, PartnerRequestEntity> serviceMapper;

    @Override
    public PartnerRequest create(String partnerTransactionId, String partner) {
        PartnerRequestEntity entity = new PartnerRequestEntity();
        entity.setPartner(partner);
        entity.setPartnerTransactionId(partnerTransactionId);
        entity.setClickId(String.valueOf(new Date().getTime()));
        entity.setCreatedAt(TimeUtil.getCurrentUTCTime());

        PartnerRequestEntity savedEntity = partnerRequestRepository.save(entity);

        return serviceMapper.mapEntityToDTO(savedEntity, PartnerRequest.class);
    }
}
