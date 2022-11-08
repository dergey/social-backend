package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.Address;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.Gender;
import com.sergey.zhuravlev.social.enums.RelationshipStatus;
import com.sergey.zhuravlev.social.exception.SocialServiceFieldException;
import com.sergey.zhuravlev.social.repository.ProfileRepository;
import com.sergey.zhuravlev.social.util.SearchStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfilePropertyService {

    private final ProfileRepository profileRepository;

    @Transactional
    public Profile updateProfileSubmission(Profile profile, String firstName, String middleName, String secondName, Gender gender) {
        profile = profileRepository.getById(profile.getId());
        profile.setFirstName(firstName);
        profile.setMiddleName(middleName);
        profile.setSecondName(secondName);
        profile.setGender(gender);
        profile.setUpdateAt(LocalDateTime.now());
        profile.setSearchString(SearchStringUtils.getSearchString(profile));
        return profile;
    }

    @Transactional
    public Profile updateProfileUsername(Profile profile, String username) {
        if (profileRepository.findByUsername(username).isPresent()) {
            throw new SocialServiceFieldException("username", ErrorCode.ALREADY_EXIST);
        }
        profile = profileRepository.getById(profile.getId());
        profile.setUsername(username);
        profile.setUpdateAt(LocalDateTime.now());
        profile.setSearchString(SearchStringUtils.getSearchString(profile));
        return profile;
    }

    @Transactional
    public Profile updateProfileBirthDate(Profile profile, LocalDate birthDate) {
        profile = profileRepository.getById(profile.getId());
        profile.setUpdateAt(LocalDateTime.now());
        profile.setBirthDate(birthDate);
        return profile;
    }

    @Transactional
    public Profile updateProfileOverview(Profile profile, String overview) {
        profile = profileRepository.getById(profile.getId());
        profile.setUpdateAt(LocalDateTime.now());
        profile.setOverview(overview);
        return profile;
    }

    @Transactional
    public Profile updateProfileRelationshipStatus(Profile profile, RelationshipStatus relationshipStatus) {
        profile = profileRepository.getById(profile.getId());
        profile.setRelationshipStatus(relationshipStatus);
        profile.setUpdateAt(LocalDateTime.now());
        return profile;
    }

    @Transactional
    public Profile updateProfileWorkplace(Profile profile, String workplace) {
        profile = profileRepository.getById(profile.getId());
        profile.setWorkplace(workplace);
        profile.setUpdateAt(LocalDateTime.now());
        return profile;
    }

    @Transactional
    public Profile updateProfileCitizenship(Profile profile, String citizenship) {
        profile = profileRepository.getById(profile.getId());
        profile.setCitizenship(citizenship);
        profile.setUpdateAt(LocalDateTime.now());
        return profile;
    }

    @Transactional
    public Profile updateRegistrationAddress(Profile profile, String firstLine, String secondLine,
                                             String city, String country, String zipCode) {
        profile = profileRepository.getById(profile.getId());
        profile.setRegistrationAddress(new Address(null, firstLine, secondLine, city, country, zipCode));
        profile.setUpdateAt(LocalDateTime.now());
        return profile;
    }

    @Transactional
    public Profile updateResidenceAddress(Profile profile, String firstLine, String secondLine,
                                          String city, String country, String zipCode) {
        profile = profileRepository.getById(profile.getId());
        profile.setResidenceAddress(new Address(null, firstLine, secondLine, city, country, zipCode));
        profile.setUpdateAt(LocalDateTime.now());
        return profile;
    }

}
