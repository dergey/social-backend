package com.sergey.zhuravlev.social.user.jpa.repository;

import com.sergey.zhuravlev.social.user.jpa.entity.NewUser;
import com.sergey.zhuravlev.social.user.jpa.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewUserRepository extends R2dbcRepository<NewUser, Long> {

    Mono<NewUser> findByConfirmationLinkCode(String linkCode);

    Mono<NewUser> findByContinuationCode(UUID continuationCode);

    Mono<NewUser> findByEmail(String email);

    Mono<NewUser> findByPhone(String phone);

}
