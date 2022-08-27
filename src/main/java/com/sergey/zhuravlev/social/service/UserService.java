package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.exception.FieldAlreadyExistsException;
import com.sergey.zhuravlev.social.repository.UserRepository;
import com.sergey.zhuravlev.social.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        String email  = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new RuntimeException("Unauthorized"));
        return getUser(email);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User not found by id %s", id)));
    }

    @Transactional(readOnly = true)
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User not found by email %s", email)));
    }

    @Transactional
    public User createUserWithEmail(String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new FieldAlreadyExistsException("Email already registered", "email", email);
        }
        User user = new User(null,
                email,
                null,
                passwordEncoder.encode(rawPassword),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);
        return userRepository.save(user);
    }

    @Transactional
    public User createUserWithPhone(String phone, String rawPassword) {
        if (userRepository.findByPhone(phone).isPresent()) {
            throw new FieldAlreadyExistsException("Phone already registered", "phone", phone);
        }
        User user = new User(null,
                null,
                phone,
                passwordEncoder.encode(rawPassword),
                LocalDateTime.now(),
                LocalDateTime.now(),
                null);
        return userRepository.save(user);
    }

}
