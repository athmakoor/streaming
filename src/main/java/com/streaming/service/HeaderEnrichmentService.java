package com.streaming.service;

import java.util.Map;

public interface HeaderEnrichmentService {
    void updateModel(Map<String, Object> model, String correlatorId, String token, Integer statusCode);
}
