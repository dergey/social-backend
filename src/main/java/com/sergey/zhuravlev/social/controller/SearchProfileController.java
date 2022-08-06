package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ProfilePreviewDto;
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
public class SearchProfileController {

    private final ProfileService profileService;

    private final ProfileMapper profileMapper;

    @GetMapping
    public Page<ProfilePreviewDto> search(@RequestParam("query") String query, @ParameterObject Pageable pageable) {
        return profileService.searchProfile(query, pageable).map(profileMapper::profileToProfilePreviewDto);
    }

}
