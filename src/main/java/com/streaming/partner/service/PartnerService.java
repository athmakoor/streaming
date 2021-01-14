package com.streaming.partner.service;

import com.streaming.partner.bean.Partner;

public interface PartnerService {
    Partner findByPartnerId(String partnerId);
}
