package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.PrivacyRules;
import com.sergey.zhuravlev.social.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivacyRulesRepository extends JpaRepository<PrivacyRules, Long> {

    PrivacyRules getByProfile(Profile profile);

    Optional<PrivacyRules> findByProfile(Profile profile);

}
