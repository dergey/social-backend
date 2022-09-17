package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

    Optional<PasswordReset> findByConfirmationLinkCode(String linkCode);
    Optional<PasswordReset> findByContinuationCode(UUID continuationCode);

    Optional<PasswordReset> findByUserEmail(String email);
    Optional<PasswordReset> findByUserPhone(String phone);
}

