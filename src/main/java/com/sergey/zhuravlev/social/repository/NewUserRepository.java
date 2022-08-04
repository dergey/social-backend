package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.NewUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NewUserRepository extends JpaRepository<NewUser, Long> {

    Optional<NewUser> findByConfirmationLinkCode(String linkCode);
    Optional<NewUser> findByContinuationCode(UUID continuationCode);

    Optional<NewUser> findByEmail(String email);
    Optional<NewUser> findByPhone(String phone);
}
