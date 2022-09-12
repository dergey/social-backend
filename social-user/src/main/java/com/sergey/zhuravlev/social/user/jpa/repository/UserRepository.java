package com.sergey.zhuravlev.social.user.jpa.repository;

import com.sergey.zhuravlev.social.user.jpa.entity.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    Mono<User> findByPhone(String phone);

    Mono<User> findByEmail(String email);

}
