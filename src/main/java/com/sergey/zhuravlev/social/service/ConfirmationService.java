package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Confirmation;
import com.sergey.zhuravlev.social.enums.ConfirmationType;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.exception.SocialServiceException;
import com.sergey.zhuravlev.social.exception.SocialServiceFieldException;
import com.sergey.zhuravlev.social.util.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Service
public class ConfirmationService {

    @Value("${confirmation.code.manual.length:5}")
    private Integer manualCodeLength;

    @Value("${confirmation.code.link.length:16}")
    private Integer linkCodeLength;

    @Value("${confirmation.code.manual.max-tries:3}")
    private Integer manualCodeMaxTries;

    @Value("${confirmation.test:false}")
    private boolean isTest;

    public Confirmation createPhoneConfirmation() {
        return new Confirmation(
                ConfirmationType.PHONE,
                generateManualCode(),
                0,
                null,
                LocalDateTime.now().plusMinutes(15));
    }

    public Confirmation createEmailConfirmation() {
        return new Confirmation(
                ConfirmationType.EMAIL,
                generateManualCode(),
                0,
                generateLinkCode(),
                LocalDateTime.now().plusHours(6));
    }

    public void resetConfirmation(Confirmation confirmation) {
        confirmation.setManualCode(generateManualCode());
        confirmation.setManualCodeTries(0);
        if (confirmation.getType().equals(ConfirmationType.EMAIL)) {
            confirmation.setLinkCode(generateLinkCode());
            confirmation.setValidUntil(LocalDateTime.now().plusHours(6));
        } else {
            confirmation.setValidUntil(LocalDateTime.now().plusMinutes(15));
        }
    }

    public boolean isActualConfirmation(Confirmation confirmation) {
        return confirmation.getValidUntil().isBefore(LocalDateTime.now()) &&
                confirmation.getManualCodeTries() < manualCodeMaxTries;
    }

    public void validateManualCode(Confirmation confirmation, String code) {
        if (confirmation.getValidUntil().isBefore(LocalDateTime.now())) {
            throw new SocialServiceException(ErrorCode.CONFIRMATION_HAS_EXPIRED);
        }
        if (!confirmation.getManualCode().equals(code)) {
            if (confirmation.getManualCodeTries() + 1 > manualCodeMaxTries) {
                throw new SocialServiceException(ErrorCode.TOO_MANY_CONFIRMATION_TRIES);
            }
            confirmation.setManualCodeTries(confirmation.getManualCodeTries() + 1);
            throw new SocialServiceFieldException("manualCode", ErrorCode.NOT_VALID);
        }
    }

    public void validateLinkCode(Confirmation confirmation, String code) {
        if (confirmation.getValidUntil().isBefore(LocalDateTime.now())) {
            throw new SocialServiceException(ErrorCode.CONFIRMATION_HAS_EXPIRED);
        }
        // The code will always be correct, because the search goes by the code.
    }

    private String generateManualCode() {
        if (isTest) {
            return Stream.generate(() -> "1").limit(manualCodeLength).collect(joining());
        } else {
            return RandomStringUtils.getNumericString(manualCodeLength);
        }
    }

    private String generateLinkCode() {
        return RandomStringUtils.getAlphaNumericString(linkCodeLength);
    }

}
