package com.streaming.subscription.controller;

import com.streaming.auth.service.AuthService;
import com.streaming.bean.AppApiReturn;
import com.streaming.constant.Provider;
import com.streaming.exception.ItemNotFoundException;
import com.streaming.partner.service.PartnerRequestService;
import com.streaming.subscription.bean.GenerateOTPRequest;
import com.streaming.subscription.bean.GenerateOTPResponse;
import com.streaming.subscription.bean.VerifyOTPRequest;
import com.streaming.subscription.bean.VerifyOTPResponse;
import com.streaming.subscription.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;

@RestController
@Validated
@RequestMapping("/app/zain-kuwait")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OtpFlowBillingController {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Resource
    private SubscriptionService subscriptionService;
    @Resource
    private PartnerRequestService partnerRequestService;
    @Resource
    private AuthService authService;

    @GetMapping("/sub/check")
    public String checkSub(@RequestParam @Size(max = 11, min = 8) String msisdn, @RequestParam String partnerId) throws ItemNotFoundException, UnsupportedEncodingException {
        return authService.checkSubscription(msisdn) ? "ACTIVE" : "INACTIVE";
    }

    @GetMapping("/sub/otp/send")
    public AppApiReturn sendOTP(@RequestParam @Size(max = 11, min = 8) String msisdn, @RequestParam String partnerId,
                                @RequestParam(required = false) String clientIp, @RequestParam String transId, @RequestParam(required = false) Boolean test, HttpServletRequest req) throws UnsupportedEncodingException {
        AppApiReturn status = null;

        if (transId == null) {
            throw new ItemNotFoundException("Transaction id not found in request");
        }
        if (StringUtils.isEmpty(msisdn)) {
            log.info("Invalid msisdn or msisdn not found in request" + msisdn);
            throw new ItemNotFoundException("Msisdn not found in request");
        }
        msisdn = msisdn.trim();

        GenerateOTPRequest data = new GenerateOTPRequest();
        data.setTransactionId(transId);
        data.setPartner(partnerId);
        data.setMsisdn(msisdn);
        data.setClickId(transId);
        data.setProvider(Provider.ZAIN_KUWAIT);

        if (authService.checkSubscription(msisdn)) {
            log.info("User already have service msisdn" + msisdn);
            return new AppApiReturn("SUBSCRIBED", "customer already subscribed: " + msisdn);
        }

        GenerateOTPResponse res = subscriptionService.generateOtp(data);;

        if (res.getErrMsg().equals("OPTIN_PREACTIVE_WAIT_CONF")) {
            log.info("User got otp, need to enter OTP to charge for service " + msisdn);
            status = new AppApiReturn("SUCCESS", "Otp Sent");
        } else if (res.getErrMsg().equals("OPTIN_ACTIVE_WAIT_CHARGING")) {
            log.info("User charged for service no need to enter otp " + msisdn);
            status = new AppApiReturn("SUCCESS", "Otp already entered, waiting for charge response");
        } else if (res.getErrMsg().equals("OPTIN_ALREADY_ACTIVE")) {
            status =new AppApiReturn("SUBSCRIBED", "customer already subscribed");
        } else if (res.getErrMsg().equals("OPTIN_MISSING_PARAM")) {
            status = new AppApiReturn("Fail", "Something went wrong, try again");
        } else if (res.getErrMsg().equals("OPTIN_CONF_MISSING_PARAM")) {
            status = new AppApiReturn("Fail", "Something went wrong, try again");
        } else if (res.getErrMsg().equals("REQUEST_IN_PROCESS")) {
            status = new AppApiReturn("SUCCESS", "Otp Sent");
        }

        partnerRequestService.create(transId, partnerId);

        log.info("/otp/sent response : " + status);

        return status;

    }

    @GetMapping("/sub/otp/confirmation")
    public AppApiReturn confirmOtp(@RequestParam @Size(max = 11, min = 8) String msisdn, @RequestParam String partnerId,
                                             @RequestParam String otp, @RequestParam String transId,
                                             @RequestParam(required = false) Boolean test, HttpServletRequest req) {
        AppApiReturn status = null;
        VerifyOTPRequest data = new VerifyOTPRequest();
        data.setMsisdn(msisdn);
        data.setOtpText(otp);
        data.setProvider(Provider.ZAIN_KUWAIT);
        data.setTransactionId(transId);
        data.setSessionId(req.getSession().getId());
        data.setPartner(partnerId);

        VerifyOTPResponse res = subscriptionService.verifyOtp(data);

        if (res.getErrMsg().equals("OPTIN_ACTIVE_WAIT_CHARGING")) {
            status = new AppApiReturn("SUCCESS", "Succesfully Subscribed, Please wait for process");
        } else if (res.getErrMsg().equals("OPTIN_ALREADY_ACTIVE")) {
            status = new AppApiReturn("SUBSCRIBED", "customer already subscribed");
        } else if (res.getErrMsg().equals("OPTIN_MISSING_PARAM")) {
            status = new AppApiReturn("Fail", "Something went wrong, try again");;
        } else if (res.getErrMsg().equals("OPTIN_CONF_MISSING_PARAM")) {
            status = new AppApiReturn("Fail", "Something went wrong, try again");;
        } else if (res.getErrMsg().equals("OPTIN_CONF_WRONG_PIN")) {
            status = new AppApiReturn("Fail", "Wrong Pin");
        } else if (res.getErrMsg().equals("FREE_PERIOD_ENABLED")) {
            status = new AppApiReturn("SUCCESS", "Free Pack Enabled");
        } else if (res.getErrMsg().equals("REQUEST_IN_PROCESS")) {
            status = new AppApiReturn("SUCCESS", "Your subscription request is in process, Please wait..");
        }
        log.info("otp confirmation response for transaction id: " + transId + " msisdn : " + msisdn + "is " + status);


        return status;

    }

    @GetMapping("/sub/unsubscription")
    public AppApiReturn unSubscription(@RequestParam @Size(max = 11, min = 8) String msisdn, @RequestParam String transId, HttpServletRequest req) {
        Boolean response = authService.unSubscribe(msisdn);

        if (response) {
            log.info("Unsubscribed response for transaction id: " + transId + " msisdn : " + msisdn + "is success");
            return new AppApiReturn("SUCCESS", "Unsubscribed");
        } else {
            log.info("Unsubscribed response for transaction id: " + transId + " msisdn : " + msisdn + "is failed");
            return new AppApiReturn("FAIL", "Failed to Unsubscribed");
        }
    }
}
