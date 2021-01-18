package com.streaming.subscription.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.streaming.auth.service.AuthService;
import com.streaming.constant.Provider;
import com.streaming.partner.bean.PartnerRequest;
import com.streaming.partner.service.PartnerRequestService;
import com.streaming.subscription.service.DigitalMarketingService;
import com.streaming.subscription.service.impl.DigitalMarketingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.service.NotificationsService;
import com.streaming.subscription.service.SubscriptionService;


@RestController
@RequestMapping("/api/subscribe")
public class SubscriptionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @Resource
    private SubscriptionService subscriptionService;
    @Resource
    private NotificationsService notificationsService;
    @Resource
    private AuthService authService;
    @Resource
    private DigitalMarketingService digitalMarketingService;
    @Resource
    private PartnerRequestService partnerRequestService;

    @Value("${web.url}")
    private String webUrl;
    @Value("${config.zain.kw.consent}")
    private String zaKwConsent;

    @GetMapping("/zain-kuwait/msisdn")
    public void getZainKuwaitMsisdn(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Msisdn Response: " + request.getQueryString());
        notificationsService.save("msisdn", request);
        String url = webUrl;
        String transactionId = request.getParameter("t_id");
        model.put("PROVIDER", Provider.ZAIN_KUWAIT);

        String msisdn = request.getParameter("msisdn");

        if (msisdn != null) {
            Boolean activeSubscription = authService.checkSubscription(msisdn);

            if (!activeSubscription) {
                LOGGER.debug("Redirecting to Consent page, TransactionId: " + transactionId);

                url = zaKwConsent.replace("{T_ID}", transactionId + "1");
            } else {
                model.put("AUTHENTICATED", true);
                model.put("MSISDN", msisdn);
            }
        }

        httpServletResponse.setHeader("Location", url);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/zain-kuwait/consent")
    public void getZainKuwaitConsent(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Consent Response: " + request.getQueryString());
        notificationsService.save("consent", request);
        String url = webUrl;
        model.put("PROVIDER", Provider.ZAIN_KUWAIT);

        String msisdn = request.getParameter("msisdn");

        if (msisdn != null) {
            Boolean activeSubscription = authService.checkSubscription(msisdn);
            model.put("AUTHENTICATED", activeSubscription);
            model.put("MSISDN", msisdn);
        }

        httpServletResponse.setHeader("Location", url);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/zain-kuwait/notification")
    public void getNotification(final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Notification Response: " + request.getQueryString());
        notificationsService.save("notification", request);
    }

    @PostMapping("/generateOTP")
    public GenerateOTPResponse generateOtp(@Valid @RequestBody final GenerateOTPRequest data) throws IOException {
        return subscriptionService.generateOtp(data);
    }

    @PostMapping("/regenerateOTP")
    public GenerateOTPResponse regenerateOTP(@Valid @RequestBody final GenerateOTPRequest data) throws IOException {
        return subscriptionService.regenerateOTP(data);
    }

    @PostMapping("/verifyOTP")
    public VerifyOTPResponse verifyOTP(@Valid @RequestBody final VerifyOTPRequest data) throws IOException {
        return subscriptionService.verifyOtp(data);
    }

    @GetMapping("/create")
    public void creaqte(final HttpServletRequest request) {
        notificationsService.create();
    }

    @GetMapping("/test-partner-notification/{partnerTransactionId}")
    public void testPartnerNotification(@PathVariable("partnerTransactionId") final String partnerTransactionId) {
        digitalMarketingService.sendPartnerNotification( partnerTransactionId);
    }
}
