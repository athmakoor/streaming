package com.streaming.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.streaming.auth.service.AuthService;
import com.streaming.constant.Provider;
import com.streaming.service.HeaderEnrichmentService;
import com.streaming.utils.CookieUtils;
import com.streaming.web.service.WebService;

@Controller
@RequestMapping(value = "")
public class WebController {
    @Resource
    private WebService webService;
    @Resource
    private HeaderEnrichmentService headerEnrichmentService;
    @Resource
    private AuthService authService;

    @GetMapping("/")
    public String base(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        model.put("HOME", true);
        return "home";
    }

    @GetMapping("/za-kw")
    public String zainKuwaitBase(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        model.put("HOME", true);
        model.put("PROVIDER", Provider.ZAIN_KUWAIT);
        return "home";
    }

    @GetMapping("/home")
    public String home(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        model.put("HOME", true);
        return "home";
    }

    @GetMapping("/category/{category}")
    public String category(@PathVariable("category") final String category, final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        model.put("CATEGORY", category);
        return "category";
    }

    @GetMapping("/play")
    public String play(final Map<String, Object> model, HttpServletRequest request) {
        if (CookieUtils.getCookie(request, "ra").isPresent() &&
                Boolean.parseBoolean(CookieUtils.getCookie(request, "ra").get().getValue())) {
            if (CookieUtils.getCookie(request, "msisdn").isPresent()) {
                model.put("AUTHENTICATED", authService.checkSubscription(CookieUtils.getCookie(request, "msisdn").get().getValue()));
            }
        }
        webService.updateDefaultModel(model);
        return "play";
    }

    @GetMapping("/subscribe")
    public String subscribe(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        return "subscribe";
    }

    @GetMapping("/confirmation")
    public String confirmation(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        return "confirmation";
    }

    @GetMapping("/redirect")
    public String redirect(final Map<String, Object> model,
                           @RequestParam(value = "correlatorId", required = false) final String correlatorId,
                           @RequestParam(value = "token", required = false) final String token,
                           @RequestParam(value = "statusCode", required = false) final Integer statusCode) {
        webService.updateDefaultModel(model);
        headerEnrichmentService.updateModel(model, correlatorId, token, statusCode);

        return "response";
    }

    @GetMapping("/info/faq")
    public String faq() {
        return "info/faq";
    }

    @GetMapping("/info/tnc")
    public String tnc() {
        return "info/tnc";
    }

    @GetMapping("/info/privacy")
    public String privacy() {
        return "info/privacy";
    }

    @GetMapping("/views/**")
    public String others(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        return "403";
    }
}
