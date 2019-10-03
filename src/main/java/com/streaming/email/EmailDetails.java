package com.streaming.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class EmailDetails {
    private String[] toEmail;
    private String templatePath;
    private String title;
    private Map<String, Object> details = new HashMap<>();
    private List<MultipartFile> attachments = new ArrayList<>();
    private String[] cc;

    public String[] getToEmail() {
        return toEmail;
    }

    public void setToEmail(final String[] toEmail) {
        this.toEmail = toEmail;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(final String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(final Map<String, Object> details) {
        this.details = details;
    }

    public List<MultipartFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(final List<MultipartFile> attachments) {
        this.attachments = attachments;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(final String[] cc) {
        this.cc = cc;
    }
}
