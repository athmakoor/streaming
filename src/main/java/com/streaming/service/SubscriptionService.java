package com.streaming.service;

import java.io.IOException;

public interface SubscriptionService {
    String subscribe(String msisdn) throws IOException;
}
