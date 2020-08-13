package com.streaming.auth;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streaming.auth.bean.AuthRequest;
import com.streaming.auth.bean.AuthResponse;
import com.streaming.auth.bean.VerifyOTP;
import com.streaming.auth.service.AuthService;
import com.streaming.subscription.bean.VerifyOTPRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private AuthService authService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    @PostMapping("/checkAndGenerateOTP")
    public AuthResponse checkAndGenerateOTP(@Valid @RequestBody final AuthRequest data, HttpServletRequest request) throws IOException {
        LOGGER.debug(getFullURL(request));
        return authService.checkAndGenerateOTP(data, request);
    }

    @GetMapping("/reGenerateOTP/{msisdn}")
    public AuthResponse reGenerateOTP(@PathVariable("msisdn") final String msisdn, HttpServletRequest request) throws IOException {
        LOGGER.debug(getFullURL(request));
        return authService.regenerateOTP(msisdn);
    }

    @PostMapping("/verifyOTP")
    public Boolean reGenerateOTP(@Valid @RequestBody final VerifyOTP data, HttpServletRequest request) throws IOException {
        LOGGER.debug(getFullURL(request));
        return authService.verifyOTP(data.getMsisdn(), data.getOtpText(), request);
    }
}
