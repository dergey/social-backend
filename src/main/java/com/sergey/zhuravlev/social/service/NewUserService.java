package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.NewUser;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.RegistrationStatus;
import com.sergey.zhuravlev.social.exception.*;
import com.sergey.zhuravlev.social.repository.NewUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewUserService {

    private final ConfirmationService confirmationService;
    private final NewUserRepository newUserRepository;

    public NewUser getNewUserByLinkCode(String linkCode) {
        return newUserRepository.findByConfirmationLinkCode(linkCode)
                .orElseThrow(() -> new SocialServiceFieldException("linkCode", ErrorCode.NOT_VALID));
    }

    public NewUser getNewUserByContinuationCode(UUID continuationCode) {
        return newUserRepository.findByContinuationCode(continuationCode)
                .orElseThrow(() -> new SocialServiceFieldException("continuationCode", ErrorCode.NOT_FOUND));
    }

    public NewUser createNewUserWithPhone(String phone) {
        return newUserRepository.save(new NewUser(null,
                UUID.randomUUID(),
                null,
                phone,
                RegistrationStatus.PHONE_CONFIRMATION,
                confirmationService.createPhoneConfirmation(),
                LocalDateTime.now()));
    }

    public NewUser createNewUserWithEmail(String email) {
        return newUserRepository.save(new NewUser(null,
                UUID.randomUUID(),
                email,
                null,
                RegistrationStatus.EMAIL_CONFIRMATION,
                confirmationService.createEmailConfirmation(),
                LocalDateTime.now()));
    }

    public void renewConfirmation(NewUser newUser) {
        confirmationService.resetConfirmation(newUser.getConfirmation());
        newUser.setContinuationCode(UUID.randomUUID());
    }

    public void confirmByManualCode(NewUser newUser, String manualCode) {
        confirmationService.validateManualCode(newUser.getConfirmation(), manualCode);
        newUser.setRegistrationStatus(RegistrationStatus.PERSONAL_DATA_AWAIT);
        newUser.setContinuationCode(UUID.randomUUID());
    }

    public void confirmByLinkCode(NewUser newUser, String linkCode) {
        confirmationService.validateLinkCode(newUser.getConfirmation(), linkCode);
        newUser.setRegistrationStatus(RegistrationStatus.PERSONAL_DATA_AWAIT);
        newUser.setContinuationCode(UUID.randomUUID());
    }

}
