package com.streaming.subscription.notification.timwe;

import com.streaming.aggregator.timwe.bean.notication.*;
import com.streaming.aggregator.timwe.constants.NotificationTypes;
import com.streaming.aggregator.timwe.service.TimWeNotificationService;
import com.streaming.constant.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/duae/notification")
public class NotificationController {
    @Resource
    private TimWeNotificationService notificationsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    @PostMapping("/mo/{partnerRole}")
    public NotificationResponse getMoNotification(@Valid @RequestBody final NotificationMoRequest data, @PathVariable("partnerRole") final String roleId, final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Timwe Mo Notification Response: " + request.getQueryString());
        LOGGER.debug(getFullURL(request));
        LOGGER.info(data.toString());
        return notificationsService.save(request, data, Provider.TIMWE, NotificationTypes.MOBILE_ORIGIN);
    }

    @PostMapping("/mt/dn/{partnerRole}")
    public NotificationResponse getMtDnNotification(@Valid @RequestBody final NotificationDNRequest data, @PathVariable("partnerRole") final String roleId, final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Timwe MT DN Notification Response: " + request.getQueryString());
        LOGGER.debug(getFullURL(request));
        LOGGER.info(data.toString());
        return notificationsService.save(request, data, Provider.TIMWE, NotificationTypes.MOBILE_TERMINATE);
    }

    @PostMapping("/subscribe/{partnerRole}")
    public NotificationResponse getOptInNotification(@Valid @RequestBody final NotificationUserOptinRequest data, @PathVariable("partnerRole") final String roleId, final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Timwe Subscribe Notification Response: " + request.getQueryString());
        LOGGER.debug(getFullURL(request));
        LOGGER.info(data.toString());
        return  notificationsService.save(request, data, Provider.TIMWE, NotificationTypes.OPT_IN);
    }

    @PostMapping("/unsubscribe/{partnerRole}")
    public NotificationResponse getOptOutNotification(@Valid @RequestBody final NotificationUserOptoutRequest data, final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Timwe UN-SUBSCRIBE Notification Response: " + request.getQueryString());
        LOGGER.debug(getFullURL(request));
        LOGGER.info(data.toString());
        return notificationsService.save(request, data, Provider.TIMWE, NotificationTypes.OPT_OUT);
    }

    @PostMapping("/renewed/{partnerRole}")
    public NotificationResponse getReNewNotification(@Valid @RequestBody final NotificationUserOptinRequest data, @PathVariable("partnerRole") final String roleId, final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Timwe RENEW Notification Response: " + request.getQueryString());
        LOGGER.debug(getFullURL(request));
        LOGGER.info(data.toString());
        return notificationsService.save(request, data, Provider.TIMWE, NotificationTypes.RE_NEWED);
    }

    @PostMapping("/charge/{partnerRole}")
    public NotificationResponse getFirstChargeNotification(@Valid @RequestBody final NotificationChargeRequest data, @PathVariable("partnerRole") final String roleId, final HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println("Timwe FIRST CHARGE Notification Response: " + request.getQueryString());
        LOGGER.debug(getFullURL(request));
        LOGGER.info(data.toString());
        return notificationsService.save(request, data, Provider.TIMWE, NotificationTypes.FIRST_CHARGE);
    }
}
