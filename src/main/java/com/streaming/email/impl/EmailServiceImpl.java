package com.streaming.email.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.streaming.email.EmailDetails;
import com.streaming.email.EmailService;

@Component
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private VelocityEngine velocityEngine;

    private ScheduledExecutorService quickService = Executors.newScheduledThreadPool(5);

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendEmail(final EmailDetails details) {
        MimeMessagePreparator preparator = getMessagePreparator(details);

        quickService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.send(preparator);
                } catch (MailException e) {
                    LOGGER.error("Exception occur while send a mail : ", e);
                }
            }
        });
    }

    private MimeMessagePreparator getMessagePreparator(final EmailDetails details) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(final MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                //message.setFrom(PropertyManager.getSentFromMailUserName());
                message.setTo(details.getToEmail());
                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("details", details.getDetails());

                StringWriter stringWriter = new StringWriter();
                velocityEngine.mergeTemplate(details.getTemplatePath(), "UTF-8", velocityContext, stringWriter);
                message.setText(stringWriter.toString(), true);
                message.setSubject(details.getTitle());
                message.setSentDate(new Date());

                if (details.getCc() != null && details.getCc().length > 0) {
                    message.setCc(details.getCc());
                }

                for (MultipartFile attachment : details.getAttachments()) {
                    message.addAttachment(attachment.getOriginalFilename(), new InputStreamSource() {

                        @Override
                        public InputStream getInputStream() throws IOException {
                            return attachment.getInputStream();
                        }
                    });
                }
            }
        };
        return preparator;
    }
}
