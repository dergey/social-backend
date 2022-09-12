package com.sergey.zhuravlev.social.user.service;

import com.sergey.zhuravlev.social.model.user.GetUserByEmailRequest;
import com.sergey.zhuravlev.social.model.user.UserModel;
import com.sergey.zhuravlev.social.user.factory.ExternalModelFactory;
import com.sergey.zhuravlev.social.user.jpa.entity.User;
import com.sergey.zhuravlev.social.user.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserModel> getUserByEmail(GetUserByEmailRequest request) {
        Mono<User> user = userRepository.findByEmail(request.getEmail());
        return user.map(ExternalModelFactory::toUserModel);
    }

}
