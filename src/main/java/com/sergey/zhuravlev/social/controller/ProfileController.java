package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ProfileDetailDto;
import com.sergey.zhuravlev.social.entity.Image;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ImageSize;
import com.sergey.zhuravlev.social.mapper.ProfileMapper;
import com.sergey.zhuravlev.social.service.ImageService;
import com.sergey.zhuravlev.social.service.ProfileService;
import com.sergey.zhuravlev.social.service.UserService;
import com.sergey.zhuravlev.social.util.ImageResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ProfileDetailDto getCurrentUserProfile() {
        User user = userService.getCurrentUser();
        Profile profile = profileService.getProfile(user);
        return profileMapper.profileToProfileDetailDto(profile);
    }

    @PostMapping("/avatar")
    public ProfileDetailDto createOrUpdateProfileAvatar(@RequestParam("image") MultipartFile multipartFile)
            throws IOException {
        User currentUser = userService.getCurrentUser();
        Profile profile = profileService.getProfile(currentUser);
        Image image = imageService.createImage(currentUser, multipartFile);
        profile = profileService.createOrUpdateProfileAvatar(profile, image);
        return profileMapper.profileToProfileDetailDto(profile);
    }

    @GetMapping("/{username}")
    public ProfileDetailDto getProfile(@PathVariable String username) {
        Profile profile = profileService.getProfile(username);
        return profileMapper.profileToProfileDetailDto(profile);
    }

    @GetMapping("/{username}/avatar")
    public ResponseEntity<byte[]> getProfileAvatar(@PathVariable String username, WebRequest request) {
        Profile profile = profileService.getProfile(username);

        // Check for data changes
        long lastModifiedTimestamp = ImageResponseUtils.toLastModifiedTimestamp(profile.getAvatar().getCreateAt());
        if (ImageResponseUtils.checkNotModified(request, lastModifiedTimestamp)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Image image = imageService.fetchImage(profile.getAvatar());
        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(lastModifiedTimestamp)
                .contentType(MediaType.valueOf(image.getMimeType()))
                .body(image.getData());
    }

    @GetMapping(value = "/{username}/avatar", params = "size")
    public ResponseEntity<byte[]> getProfileAvatarWithSize(@PathVariable String username,
                                                           @RequestParam(name = "size") ImageSize size,
                                                           WebRequest request) {
        Profile profile = profileService.getProfile(username);

        // Check for data changes
        long lastModifiedTimestamp = ImageResponseUtils.toLastModifiedTimestamp(profile.getAvatar().getCreateAt());
        if (ImageResponseUtils.checkNotModified(request, lastModifiedTimestamp)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        Image image = imageService.fetchPreviewImage(profile.getAvatar());
        return ResponseEntity
                .status(HttpStatus.OK)
                .lastModified(lastModifiedTimestamp)
                .contentType(MediaType.valueOf(image.getMimeType()))
                .body(image.getPreview());
    }


}
