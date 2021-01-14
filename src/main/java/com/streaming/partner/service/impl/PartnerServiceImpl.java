package com.streaming.partner.service.impl;

import com.streaming.partner.bean.Partner;
import com.streaming.partner.bean.jpa.PartnerEntity;
import com.streaming.partner.repository.PartnerRepository;
import com.streaming.partner.service.PartnerService;
import com.streaming.service.mapping.ServiceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PartnerServiceImpl implements PartnerService {
    @Resource
    private PartnerRepository partnerRepository;
    @Resource
    private ServiceMapper<Partner, PartnerEntity> serviceMapper;

    @Override
    public Partner findByPartnerId(String partnerId) {
        PartnerEntity partnerEntity = partnerRepository.findByPartnerId(partnerId).get();

        return serviceMapper.mapEntityToDTO(partnerEntity, Partner.class);
    }

}
