package com.streaming.partner.service.impl;

import com.streaming.constant.Provider;
import com.streaming.partner.bean.Partner;
import com.streaming.partner.bean.jpa.PartnerEntity;
import com.streaming.partner.bean.jpa.PartnerRequestEntity;
import com.streaming.partner.repository.PartnerRepository;
import com.streaming.partner.repository.PartnerRequestRepository;
import com.streaming.partner.service.PartnerService;
import com.streaming.service.mapping.ServiceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService {
    @Value("${config.zain.kw.consent}")
    private String zaKwConsent;

    @Resource
    private PartnerRepository partnerRepository;
    @Resource
    private ServiceMapper<Partner, PartnerEntity> serviceMapper;
    @Resource
    private PartnerRequestRepository partnerRequestRepository;

    @Override
    public Partner findByPartnerId(String partnerId) {
        PartnerEntity partnerEntity = partnerRepository.findByPartnerId(partnerId).get();

        return serviceMapper.mapEntityToDTO(partnerEntity, Partner.class);
    }

    @Override
    public String getConsentUrlClickIdAndProvider(String transactionId, String provider) {
        if (Provider.ZAIN_KUWAIT.equals(provider)) {
            return getZainKuwaitConsentUrl(transactionId);
        }

        return null;
    }

    private String getZainKuwaitConsentUrl(String transactionId) {
        List<PartnerRequestEntity> partnerRequestEntities = partnerRequestRepository.findByClickId(transactionId);

        if (partnerRequestEntities.isEmpty()) {
            return zaKwConsent;
        }

        PartnerRequestEntity partnerRequestEntity = partnerRequestEntities.get(0);

        if (StringUtils.isEmpty(partnerRequestEntity.getPartner())) {
            return zaKwConsent;
        }

        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findByPartnerId(partnerRequestEntity.getPartner());

        if (!partnerEntityOptional.isPresent()) {
            return zaKwConsent;
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();

        if (StringUtils.isEmpty(partnerEntity.getRedirectUrl())) {
            return zaKwConsent;
        }

        return partnerEntity.getRedirectUrl();
    }

}
