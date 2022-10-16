package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.mail.MailFrontendContext;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import javax.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;
    private final ResourceBundleMessageSource mailSubjectMessageSource;
    private final MailFrontendContext mailFrontendContext;

    @Async
    public void sendTemplate(String recipient, String subjectKey, String template, Object rootObject) {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
            mimeMessageHelper.setFrom("norelay@social.xpolr.space");
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject(this.mailSubjectMessageSource.getMessage(subjectKey, null, Locale.ENGLISH));
            mimeMessageHelper.setText(this.getEmailContent(template, rootObject), true);
            this.javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.warn("Email send fail: ", e);
        }

    }

    private String getEmailContent(String template, Object rootObject) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        ModelMap model = new ModelMap();
        model.addAttribute("root", rootObject);
        model.addAttribute("frontendContext", this.mailFrontendContext);
        this.configuration.getTemplate(template).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}
