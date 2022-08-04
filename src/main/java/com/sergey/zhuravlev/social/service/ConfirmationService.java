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

@Service
public class ConfirmationService {

    @Value("${confirmation.code.manual.length:4}")
    private Integer manualCodeLength;

    @Value("${confirmation.code.link.length:16}")
    private Integer linkCodeLength;

    @Value("${confirmation.code.manual.max-tries:3}")
    private Integer manualCodeMaxTries;

    public Confirmation createPhoneConfirmation() {
        return new Confirmation(
                ConfirmationType.PHONE,
                RandomStringUtils.getNumericString(manualCodeLength),
                0,
                null,
                LocalDateTime.now().plusMinutes(15));
    }

    public Confirmation createEmailConfirmation() {
        return new Confirmation(
                ConfirmationType.EMAIL,
                RandomStringUtils.getNumericString(manualCodeLength),
                0,
                RandomStringUtils.getAlphaNumericString(linkCodeLength),
                LocalDateTime.now().plusHours(6));
    }

    public void resetConfirmation(Confirmation confirmation) {
        confirmation.setManualCode(RandomStringUtils.getNumericString(manualCodeLength));
        confirmation.setManualCodeTries(0);
        if (confirmation.getType().equals(ConfirmationType.EMAIL)) {
            confirmation.setLinkCode(RandomStringUtils.getAlphaNumericString(linkCodeLength));
            confirmation.setValidUntil(LocalDateTime.now().plusHours(6));
        } else {
            confirmation.setValidUntil(LocalDateTime.now().plusMinutes(15));
        }
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

}
