package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ProfilePreviewDto;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.mapper.ProfileMapper;
import com.sergey.zhuravlev.social.service.FriendService;
import com.sergey.zhuravlev.social.service.ProfileService;
import com.sergey.zhuravlev.social.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final ProfileService profileService;

    private final ProfileMapper profileMapper;

    @GetMapping("/friend")
    public Page<ProfilePreviewDto> getCurrentUserFriends(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        return friendService.getProfileFriends(currentProfile, pageable)
                .map(profileMapper::profileToProfilePreviewDto);
    }

    @GetMapping("/friend/requests")
    public Page<ProfilePreviewDto> getCurrentUserIncomingFriendRequests(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        return friendService.getProfileIncomingFriendRequests(currentProfile, pageable)
                .map(profileMapper::profileToProfilePreviewDto);
    }

    @GetMapping("/profile/{username}/friend")
    public Page<ProfilePreviewDto> getProfileFriends(@PathVariable String username, Pageable pageable) {
        Profile profile = profileService.getProfile(username);
        return friendService.getProfileFriends(profile, pageable)
                .map(profileMapper::profileToProfilePreviewDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/profile/{username}/friend/request")
    public void createFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.createFriendRequest(currentProfile, targetProfile);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/friend/request/{username}/accept")
    public void acceptFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.acceptFriendRequest(currentProfile, targetProfile);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/friend/request/{username}/decline")
    public void declineFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.declineFriendRequest(currentProfile, targetProfile);
    }

}
