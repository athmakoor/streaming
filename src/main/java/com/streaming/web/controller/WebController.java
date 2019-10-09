package com.streaming.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.streaming.web.service.WebService;

@Controller
@RequestMapping(value = "")
public class WebController {
    @Resource
    private WebService webService;

    @GetMapping("/")
    public String base(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        return "home";
    }

    @GetMapping("/home")
    public String home(final Map<String, Object> model) {
        webService.updateDefaultModel(model);
        return "home";
    }

    @GetMapping("/play")
    public String play(final Map<String, Object> model) {
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
