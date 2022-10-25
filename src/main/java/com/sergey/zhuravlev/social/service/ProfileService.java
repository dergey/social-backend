package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.entity.predicate.ProfilePredicateBuilder;
import com.sergey.zhuravlev.social.enums.Gender;
import com.sergey.zhuravlev.social.exception.FieldAlreadyExistsException;
import com.sergey.zhuravlev.social.repository.ProfileRepository;
import com.sergey.zhuravlev.social.security.SecurityUtils;
import com.sergey.zhuravlev.social.util.SearchStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public Page<Profile> searchProfile(ProfilePredicateBuilder builder, Pageable pageable) {
        return profileRepository.findAll(builder.build(), pageable);
    }

    @Transactional(readOnly = true)
    public Profile getProfile(String username) {
        return profileRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Profile not found by username %s", username)));
    }

    @Transactional(readOnly = true)
    public Profile getProfile(User user) {
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found for user"));
    }

    @Transactional(readOnly = true)
    public Profile getCurrentProfile() {
        return SecurityUtils.extractProfileId()
            .flatMap(profileRepository::findById)
            .orElseThrow(() -> new EntityNotFoundException("Current profile not found"));
    }

    @Transactional
    public Profile createProfile(User user, String username, Image avatar, String firstName, String middleName,
                                 String secondName, Gender gender, LocalDate birthDate) {
        if (profileRepository.findByUser(user).isPresent()) {
            throw new FieldAlreadyExistsException("User already have profile", "user", user.getEmail());
        }
        if (profileRepository.findByUsername(username).isPresent()) {
            throw new FieldAlreadyExistsException("Username already exists", "username", username);
        }
        Profile profile = new Profile(null,
                user,
                username,
                avatar,
                Collections.emptyList(),
                Collections.emptyList(),
                firstName,
                middleName,
                secondName,
                gender,
                birthDate,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                null);
        profile.setSearchString(SearchStringUtils.getSearchString(profile));
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile createOrUpdateProfileAvatar(Profile profile, Image image) {
        profile = profileRepository.getById(profile.getId());
        profile.setAvatar(image);
        return profile;
    }

}
