package com.streaming.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscribe")
public class SubscriptionController {
    @Resource
    private SubscriptionService subscriptionService;

    @GetMapping("/{msisdn}")
    public String subscribe(@PathVariable("msisdn") final String msisdn) throws IOException {
        return subscriptionService.subscribe(msisdn);
    }
}
