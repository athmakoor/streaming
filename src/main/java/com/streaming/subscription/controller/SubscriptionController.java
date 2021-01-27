package com.streaming.subscription.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.streaming.auth.service.AuthService;
import com.streaming.constant.Provider;
import com.streaming.partner.bean.PartnerRequest;
import com.streaming.partner.service.PartnerRequestService;
import com.streaming.partner.service.PartnerService;
import com.streaming.subscription.service.DigitalMarketingService;
import com.streaming.subscription.service.impl.DigitalMarketingServiceImpl;
import com.streaming.utils.IpUtil;
import com.streaming.utils.TimeUtil;
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
    @Resource
    private PartnerService partnerService;

    @Value("${web.url}")
    private String webUrl;

    @GetMapping("/zain-kuwait/msisdn")
    public void getZainKuwaitMsisdn(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Msisdn Response: " + request.getQueryString());
        notificationsService.save("msisdn", request);
        String url = webUrl;
        String transactionId = request.getParameter("tid");
        model.put("PROVIDER", Provider.ZAIN_KUWAIT);

        String msisdn = request.getParameter("msisdn");

        if (msisdn != null && transactionId != null) {
            Boolean activeSubscription = authService.checkSubscription(msisdn);

            if (!activeSubscription) {
                partnerRequestService.updateMsisdnByClickId(transactionId, msisdn);
                ZonedDateTime now = TimeUtil.getCurrentUTCTime();

                String year = String.valueOf(now.getYear());
                int month = now.getMonth().getValue();
                int date = now.getDayOfMonth();
                int hour = now.getHour();
                int minutes = now.getMinute();
                int seconds = now.getSecond();
                String monthString = month < 10 ? "0" + month : String.valueOf(month);
                String dateString = date < 10 ? "0" + date : String.valueOf(date);
                String hourString = hour < 10 ? "0" + hour : String.valueOf(hour);
                String minutesString = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
                String secondsString = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
                url = partnerService.getConsentUrlClickIdAndProvider(transactionId, Provider.ZAIN_KUWAIT);
                String startTime = dateString + monthString + year + hourString + minutesString + secondsString;

                url = url.replace("{T_ID}", transactionId);
                url = url.replace("{DATE}", startTime);
                url = url.replace("{SESSION_ID}", request.getSession().getId());
                url = url.replace("{USER_IP}", IpUtil.getClientIpAddr(request));
                url = url.replace("{MSISDN}", msisdn);
                LOGGER.debug("Redirecting to Consent page: " + url);
            } else {
                model.put("AUTHENTICATED", true);
                model.put("MSISDN", msisdn);
            }
        }

        httpServletResponse.setHeader("Location", url);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/zain-kuwait/consent-active")
    public void getZainKuwaitConsentActive(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        System.out.println("Zain Kuwait Consent Active Response: " + request.getQueryString());
        notificationsService.save("consent-active", request);
        String url = webUrl;
        model.put("PROVIDER", Provider.ZAIN_KUWAIT);

        String msisdn = request.getParameter("msisdn");

        if (msisdn != null) {
            digitalMarketingService.saveSubscription(msisdn, "600", "AED", Provider.ZAIN_KUWAIT, "", null);
            model.put("AUTHENTICATED", true);
            model.put("MSISDN", msisdn);
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

        String pageType = request.getParameter("pageType");

        if (pageType != null) {
            model.put("MESSAGE", "Subscription trial count exceeded.");
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
