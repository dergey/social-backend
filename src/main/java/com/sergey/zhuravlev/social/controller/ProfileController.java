package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ProfileDto;
import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ImageSize;
import com.sergey.zhuravlev.social.mapper.ProfileMapper;
import com.sergey.zhuravlev.social.service.ImageService;
import com.sergey.zhuravlev.social.service.ProfileService;
import com.sergey.zhuravlev.social.service.UserService;
import com.sergey.zhuravlev.social.util.ImageResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Profile endpoints")
@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final ImageService imageService;
    private final ProfileService profileService;

    private final ProfileMapper profileMapper;

    @GetMapping
    public ProfileDto getCurrentUserProfile() {
        User user = userService.getCurrentUser();
        Profile profile = profileService.getProfile(user);
        return profileMapper.profileToProfileDto(profile);
    }

    @PostMapping("/avatar")
    public ProfileDto createOrUpdateProfileAvatar(@RequestParam("image") MultipartFile multipartFile)
            throws IOException {
        User currentUser = userService.getCurrentUser();
        Profile profile = profileService.getProfile(currentUser);
        Image image = imageService.createImage(currentUser, multipartFile);
        profile = profileService.createOrUpdateProfileAvatar(profile, image);
        return profileMapper.profileToProfileDto(profile);
    }

    @GetMapping("/{username}")
    public ProfileDto getProfile(@PathVariable String username) {
        Profile profile = profileService.getProfile(username);
        return profileMapper.profileToProfileDto(profile);
    }

    @GetMapping("/{username}/avatar")
    public ResponseEntity<Resource> getProfileAvatar(@PathVariable String username, WebRequest request) {
        Profile profile = profileService.getProfile(username);

        // Check for data changes
        long lastModifiedTimestamp = ImageResponseUtils.toLastModifiedTimestamp(profile.getAvatar().getCreateAt());
        if (ImageResponseUtils.checkNotModified(request, lastModifiedTimestamp)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Resource image = imageService.fetchImageResource(profile.getAvatar());
        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(lastModifiedTimestamp)
                .contentType(MediaType.valueOf(profile.getAvatar().getMimeType()))
                .body(image);
    }

    @GetMapping(value = "/{username}/avatar", params = "size")
    public ResponseEntity<Resource> getProfileAvatarPreview(@PathVariable String username,
                                                            @RequestParam(name = "size") ImageSize size,
                                                            WebRequest request) {
        Profile profile = profileService.getProfile(username);

        // Check for data changes
        long lastModifiedTimestamp = ImageResponseUtils.toLastModifiedTimestamp(profile.getAvatar().getCreateAt());
        if (ImageResponseUtils.checkNotModified(request, lastModifiedTimestamp)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Resource resource = imageService.fetchPreviewImageResource(profile.getAvatar(), size.getWidth(), size.getHeight());
        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(lastModifiedTimestamp)
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }


}
