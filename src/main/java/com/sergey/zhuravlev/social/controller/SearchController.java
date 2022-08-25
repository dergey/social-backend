package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ProfilePreviewDto;
import com.sergey.zhuravlev.social.entity.predicate.ProfilePredicateBuilder;
import com.sergey.zhuravlev.social.enums.RelationshipStatus;
import com.sergey.zhuravlev.social.mapper.ProfileMapper;
import com.sergey.zhuravlev.social.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search endpoints")
@Slf4j
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final ProfileService profileService;

    private final ProfileMapper profileMapper;

    @GetMapping
    public Page<ProfilePreviewDto> searchProfile(@RequestParam String query,
                                                 @RequestParam(required = false) String country,
                                                 @RequestParam(required = false) String city,
                                                 @RequestParam(required = false) RelationshipStatus relationshipStatus,
                                                 @RequestParam(required = false) Integer age,
                                                 @RequestParam(required = false) Integer ageFrom,
                                                 @RequestParam(required = false) Integer ageTo,
                                                 @ParameterObject Pageable pageable) {
        ProfilePredicateBuilder builder = new ProfilePredicateBuilder()
                .withCountry(country)
                .withCity(city)
                .withRelationshipStatus(relationshipStatus)
                .withAge(age)
                .withAgeBetween(ageFrom, ageTo)
                .withQuery(query);

        return profileService.searchProfile(builder, pageable).map(profileMapper::profileToProfilePreviewDto);
    }

}
