package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.PasswordReset;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.PasswordResetStatus;
import com.sergey.zhuravlev.social.exception.SocialServiceException;
import com.sergey.zhuravlev.social.exception.SocialServiceFieldException;
import com.sergey.zhuravlev.social.repository.PasswordResetRepository;
import com.sergey.zhuravlev.social.repository.UserRepository;
import com.sergey.zhuravlev.social.contrains.PatternConstrains;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;

    private final ConfirmationService confirmationService;

    @Transactional
    public PasswordReset createPasswordResetByEmail(String email) {
        passwordResetRepository.findByUserEmail(email)
                .filter(r -> !r.getStatus().isFinal() && confirmationService.isActualConfirmation(r.getConfirmation()))
                .ifPresent(r -> { throw new SocialServiceException(ErrorCode.IN_USE); });
        return userRepository.findByEmail(email)
                .map(this::createPasswordResetByEmail)
                .map(passwordResetRepository::save)
                .orElseThrow(() -> new SocialServiceException(ErrorCode.NOT_FOUND));
    }

    @Transactional
    public PasswordReset createPasswordResetByPhone(String phone) {
        passwordResetRepository.findByUserPhone(phone)
                .filter(r -> !r.getStatus().isFinal() && confirmationService.isActualConfirmation(r.getConfirmation()))
                .ifPresent(r -> { throw new SocialServiceException(ErrorCode.IN_USE); });
        return userRepository.findByPhone(phone)
                .map(this::createPasswordResetByPhone)
                .map(passwordResetRepository::save)
                .orElseThrow(() -> new SocialServiceException(ErrorCode.NOT_FOUND));
    }

    private PasswordReset createPasswordResetByEmail(User userWithEmail) {
        return new PasswordReset(userWithEmail.getId(),
                userWithEmail,
                PasswordResetStatus.EMAIL_CONFIRMATION,
                UUID.randomUUID(),
                confirmationService.createEmailConfirmation());
    }

    private PasswordReset createPasswordResetByPhone(User userWithPhone) {
        return new PasswordReset(userWithPhone.getId(),
                userWithPhone,
                PasswordResetStatus.PHONE_CONFIRMATION,
                UUID.randomUUID(),
                confirmationService.createPhoneConfirmation());
    }

    @Transactional(noRollbackFor = {SocialServiceException.class})
    public PasswordReset confirmPasswordResetByManualCode(UUID continuationCode, String manualCode) {
        PasswordReset passwordReset = passwordResetRepository.findByContinuationCode(continuationCode)
                .orElseThrow(() -> new SocialServiceFieldException("continuationCode", ErrorCode.NOT_FOUND));
        if (!passwordReset.getStatus().equals(PasswordResetStatus.EMAIL_CONFIRMATION) &&
                !passwordReset.getStatus().equals(PasswordResetStatus.PHONE_CONFIRMATION)) {
            throw new SocialServiceException(ErrorCode.INVALID_PASSWORD_RESET_STATE);
        }
        confirmationService.validateManualCode(passwordReset.getConfirmation(), manualCode);
        passwordReset.setStatus(PasswordResetStatus.PASSWORD_AWAIT);
        passwordReset.setContinuationCode(UUID.randomUUID());
        return passwordReset;
    }

    @Transactional(noRollbackFor = {SocialServiceException.class})
    public PasswordReset confirmPasswordResetByLinkCode(String linkCode) {
        PasswordReset passwordReset = passwordResetRepository.findByConfirmationLinkCode(linkCode)
                .orElseThrow(() -> new SocialServiceFieldException("continuationCode", ErrorCode.NOT_FOUND));
        if (!passwordReset.getStatus().equals(PasswordResetStatus.EMAIL_CONFIRMATION) &&
                !passwordReset.getStatus().equals(PasswordResetStatus.PHONE_CONFIRMATION)) {
            throw new SocialServiceException(ErrorCode.INVALID_PASSWORD_RESET_STATE);
        }
        confirmationService.validateLinkCode(passwordReset.getConfirmation(), linkCode);
        passwordReset.setStatus(PasswordResetStatus.PASSWORD_AWAIT);
        passwordReset.setContinuationCode(UUID.randomUUID());
        return passwordReset;
    }

    @Transactional
    public PasswordReset regeneratePasswordResetConfirmation(UUID continuationCode) {
        PasswordReset passwordReset = passwordResetRepository.findByContinuationCode(continuationCode)
                .orElseThrow(() -> new SocialServiceFieldException("continuationCode", ErrorCode.NOT_FOUND));
        if (!passwordReset.getStatus().equals(PasswordResetStatus.EMAIL_CONFIRMATION) &&
                !passwordReset.getStatus().equals(PasswordResetStatus.PHONE_CONFIRMATION)) {
            throw new SocialServiceException(ErrorCode.INVALID_PASSWORD_RESET_STATE);
        }
        confirmationService.resetConfirmation(passwordReset.getConfirmation());
        passwordReset.setContinuationCode(UUID.randomUUID());
        return passwordReset;
    }

    @Transactional
    public PasswordReset removePasswordReset(UUID continuationCode) {
        PasswordReset passwordReset = passwordResetRepository.findByContinuationCode(continuationCode)
                .orElseThrow(() -> new SocialServiceFieldException("continuationCode", ErrorCode.NOT_FOUND));
        if (!passwordReset.getStatus().equals(PasswordResetStatus.PASSWORD_AWAIT)) {
            throw new SocialServiceException(ErrorCode.INVALID_PASSWORD_RESET_STATE);
        }
        passwordResetRepository.delete(passwordReset);
        return passwordReset;
    }

}
