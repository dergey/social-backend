package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.contrains.PatternConstrains;
import com.sergey.zhuravlev.social.entity.PasswordReset;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.exception.SocialServiceFieldException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class PasswordResetFlowService {

    private final PasswordResetService passwordResetService;
    private final UserService userService;
    private final EmailService emailService;

    public PasswordReset startPasswordReset(String phoneOrEmail) {
        Matcher matcher = PatternConstrains.EMAIL_OR_PHONE_PATTERN.matcher(phoneOrEmail);
        if (!matcher.matches()) {
            throw new SocialServiceFieldException("phoneOrEmail", ErrorCode.INVALID_EMAIL_OR_PHONE_FORMAT);
        }
        String phone = matcher.group(1);
        String email = matcher.group(2);
        PasswordReset passwordReset;
        if (Strings.isNotBlank(email)) {
            passwordReset = passwordResetService.createPasswordResetByEmail(email);
        } else if (Strings.isNotBlank(phone)) {
            passwordReset = passwordResetService.createPasswordResetByPhone(phone);
        } else {
            throw new IllegalArgumentException("phoneOrEmail");
        }

        emailService.sendTemplate(email,
                "reset-password.confirmation",
                "password-reset.confirmation.ftlh",
                passwordReset.getConfirmation());

        return passwordReset;
    }


    public PasswordReset confirmPasswordResetByManualCode(UUID continuationCode, String manualCode) {
        return passwordResetService.confirmPasswordResetByManualCode(continuationCode, manualCode);
    }

    public PasswordReset confirmPasswordResetByLinkCode(String linkCode) {
        return passwordResetService.confirmPasswordResetByLinkCode(linkCode);
    }

    public PasswordReset resendPasswordResetConfirmation(UUID continuationCode) {
        PasswordReset passwordReset = passwordResetService.regeneratePasswordResetConfirmation(continuationCode);
        Optional.ofNullable(passwordReset.getUser().getEmail()).ifPresent(email -> {
            emailService.sendTemplate(email,
                    "reset-password.confirmation",
                    "password-reset.confirmation.ftlh",
                    passwordReset.getConfirmation());
        });
        return passwordReset;
    }

    public void completePasswordReset(UUID continuationCode, String rawPassword) {
        PasswordReset passwordReset = passwordResetService.removePasswordReset(continuationCode);
        userService.updateUserPassword(passwordReset.getUser().getId(), rawPassword);
    }

}
