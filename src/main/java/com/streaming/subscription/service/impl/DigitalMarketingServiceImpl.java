package com.streaming.subscription.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import com.streaming.partner.bean.jpa.PartnerEntity;
import com.streaming.partner.bean.jpa.PartnerNotificationConfigEntity;
import com.streaming.partner.bean.jpa.PartnerRequestEntity;
import com.streaming.partner.repository.PartnerNotificationConfigRepository;
import com.streaming.partner.repository.PartnerRepository;
import com.streaming.partner.repository.PartnerRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.streaming.subscription.service.DigitalMarketingService;
import com.streaming.utils.request.Request;
import com.streaming.utils.request.RequestException;
import com.streaming.utils.request.RequestUtils;

import javax.annotation.Resource;

@Service
public class DigitalMarketingServiceImpl implements DigitalMarketingService {
    @Resource
    private PartnerRequestRepository partnerRequestRepository;
    @Resource
    private PartnerRepository partnerRepository;
    @Resource
    private PartnerNotificationConfigRepository partnerNotificationConfigRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalMarketingServiceImpl.class);

    private static final String NEW_SUBSCRIPTION_URL = "http://dm.vkandigital.com/api/subscription/internal-subscribe?msisdn={{MSISDN}}&cur={{CURRENCY}}&price={{PRICE}}&status={{STATUS}}&provider={{PROVIDER}}&partner={{PARTNER}}";

    @Override
    public void saveSubscription(String msisdn, String price, String currency, String provider, String partnerTransactionId, String partner) throws UnsupportedEncodingException {
        saveSubscription(msisdn,price, currency, provider, partner, partnerTransactionId, "subscribe");
    }

    @Override
    public void saveSubscription(String msisdn, String price, String currency, String provider, String partner, String partnerTransactionId, String status) throws UnsupportedEncodingException {
        String url = NEW_SUBSCRIPTION_URL.replace("{{MSISDN}}", URLEncoder.encode(msisdn, "UTF-8"));

        url = url.replace("{{CURRENCY}}", currency);
        url = url.replace("{{PRICE}}", price);
        url = url.replace("{{STATUS}}", status);
        url = url.replace("{{PROVIDER}}", provider);
        url = url.replace("{{PARTNER}}", partner);

        Request request = new Request();

        try {
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            LOGGER.debug("Saved Subscription "  + msisdn + " " + price);
            LOGGER.debug(response);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }

        if ("subscribe".equals(status)) {
            sendPartnerNotification(partnerTransactionId);
        }
    }

    @Override
    public void sendPartnerNotification(String partnerTransactionId) {
        if (partnerTransactionId == null || partnerTransactionId.isEmpty()) {
            return;
        }

        List<PartnerRequestEntity> partnerRequestEntityList = partnerRequestRepository.findByPartnerTransactionId(partnerTransactionId);

        if (partnerRequestEntityList.isEmpty()) {
            return;
        }

        Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findByPartnerId(partnerRequestEntityList.get(0).getPartner());

        if (!partnerEntityOptional.isPresent()) {
            return;
        }

        PartnerEntity partnerEntity = partnerEntityOptional.get();
        Optional<PartnerNotificationConfigEntity> partnerNotificationConfigOptional = partnerNotificationConfigRepository.findByPartnerId(partnerEntity.getPartnerId());

        if (partnerNotificationConfigOptional.isPresent()) {
            PartnerNotificationConfigEntity partnerNotificationConfigEntity = partnerNotificationConfigOptional.get();

            Double random = Math.random() * 100;

            if (random > partnerNotificationConfigEntity.getNotificationPercent()) {
                LOGGER.debug("Partner Notification Ignored: "  + partnerTransactionId);
            }
        }

        Request request = new Request();

        try {
            String url = partnerEntityOptional.get().getCallbackUrl();
            url = url.replace("{T_ID}", partnerTransactionId);
            request.setMethod("GET");
            request.setPath(url);
            String response = RequestUtils.getResponse(request, null);

            LOGGER.debug("Partner Notification t_id: "  + partnerTransactionId);
            LOGGER.debug(response);
        } catch (RequestException e) {
            e.printStackTrace();
            throw new RequestException(e.getMessage());
        }
    }
}
