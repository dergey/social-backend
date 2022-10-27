package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.ProfilePreviewDto;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import com.sergey.zhuravlev.social.enums.ProfileAttitude;
import com.sergey.zhuravlev.social.mapper.ProfileMapper;
import com.sergey.zhuravlev.social.service.FriendService;
import com.sergey.zhuravlev.social.service.ProfileAttitudeService;
import com.sergey.zhuravlev.social.service.ProfileService;
import com.sergey.zhuravlev.social.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Friend endpoints")
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final ProfileService profileService;
    private final ProfileAttitudeService profileAttitudeService;

    private final ProfileMapper profileMapper;

    @Operation(description = "Gets a list of friends of the current user")
    @GetMapping("/friend")
    public Page<ProfilePreviewDto> getCurrentUserFriends(@ParameterObject Pageable pageable) {
        Profile currentProfile = profileService.getCurrentProfile();
        Page<Profile> friendPage = friendService.getProfileFriends(currentProfile, pageable);
        profileAttitudeService.setAttitudesForce(friendPage, ProfileAttitude.FRIEND);
        return friendPage.map(profileMapper::profileToProfilePreviewDto);
    }

    @Operation(description = "Deletes the current user's friend")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/friend/{username}")
    public void removeFriend(@PathVariable String username) {
        Profile currentProfile = profileService.getCurrentProfile();
        Profile friend = profileService.getProfile(username);
        friendService.removeProfileFriend(currentProfile, friend);
    }

    @Operation(description = "Gets a list of incoming friend requests of the current user")
    @GetMapping("/friend/incoming")
    public Page<ProfilePreviewDto> getCurrentUserIncomingFriendRequests(@ParameterObject Pageable pageable) {
        Profile currentProfile = profileService.getCurrentProfile();
        Page<Profile> incomingFriendPage = friendService.getProfileIncomingFriendRequests(currentProfile, pageable);
        profileAttitudeService.setAttitudesForce(incomingFriendPage, ProfileAttitude.FRIEND_INCOMING);
        return incomingFriendPage.map(profileMapper::profileToProfilePreviewDto);
    }

    @Operation(description = "Gets a list of outgoing friend requests of the current user")
    @GetMapping("/friend/outgoing")
    public Page<ProfilePreviewDto> getCurrentUserOutgoingFriendRequests(@ParameterObject Pageable pageable) {
        Profile currentProfile = profileService.getCurrentProfile();
        Page<Profile> outgoingFriendPage = friendService.getProfileOutgoingFriendRequests(currentProfile, pageable);
        profileAttitudeService.setAttitudesForce(outgoingFriendPage, ProfileAttitude.FRIEND_OUTGOING);
        return outgoingFriendPage.map(profileMapper::profileToProfilePreviewDto);
    }

    @Operation(description = "Gets a list of friends of the user")
    @GetMapping("/profile/{username}/friend")
    public Page<ProfilePreviewDto> getProfileFriends(@PathVariable String username, @ParameterObject Pageable pageable) {
        Profile profile = profileService.getProfile(username);
        Page<Profile> friendPage = friendService.getProfileFriends(profile, pageable);
        Profile profileAspect = profileService.getCurrentProfile();
        profileAttitudeService.setAttitudes(friendPage, profileAspect);
        return friendPage.map(profileMapper::profileToProfilePreviewDto);
    }

    @Operation(description = "Creates a friend request to the specified user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/profile/{username}/friend/request")
    public void createFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.createFriendRequest(currentProfile, targetProfile);
    }

    @Operation(description = "Revokes a friend request of the specified user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/profile/{username}/friend/request")
    public void revokeFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.revokeFriendRequest(currentProfile, targetProfile);
    }

    @Operation(description = "Accepts friend requests from the specified user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/friend/incoming/{username}/accept")
    public void acceptFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.acceptFriendRequest(currentProfile, targetProfile);
    }

    @Operation(description = "Decline friend requests from the specified user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/friend/incoming/{username}/decline")
    public void declineFriendRequest(@PathVariable String username) {
        User currentUser = userService.getCurrentUser();
        Profile currentProfile = profileService.getProfile(currentUser);
        Profile targetProfile = profileService.getProfile(username);
        friendService.declineFriendRequest(currentProfile, targetProfile);
    }

}
