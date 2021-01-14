package com.streaming.partner.service;

import com.streaming.partner.bean.PartnerRequest;

public interface PartnerRequestService {
    PartnerRequest create(String partnerTransactionId, String partner);
}
