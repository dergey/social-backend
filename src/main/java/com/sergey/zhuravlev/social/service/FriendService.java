package com.sergey.zhuravlev.social.service;

import com.sergey.zhuravlev.social.entity.FriendRequest;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.FriendRequestStatus;
import com.sergey.zhuravlev.social.exception.AlreadyExistsException;
import com.sergey.zhuravlev.social.exception.ObjectNotFoundException;
import com.sergey.zhuravlev.social.exception.SocialServiceException;
import com.sergey.zhuravlev.social.repository.FriendRequestRepository;
import com.sergey.zhuravlev.social.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendService {

    private final ProfileRepository profileRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Value("#{T(java.time.Duration).parse('${social.friend-request.timeout.period}')}")
    private Duration friendRequestTimeoutPeriod;

    @Transactional(readOnly = true)
    public Page<Profile> getProfileFriends(Profile currentProfile, Pageable pageable) {
        return profileRepository.findAllByFriendInProfile(currentProfile, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Profile> getProfileIncomingFriendRequests(Profile currentProfile, Pageable pageable) {
        return profileRepository.findAllByProfileTargetInFriendRequest(currentProfile, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Profile> getProfileOutgoingFriendRequests(Profile currentProfile, Pageable pageable) {
        return profileRepository.findAllByProfileSourceInFriendRequest(currentProfile, pageable);
    }

    @Transactional
    public void createFriendRequest(Profile currentProfile, Profile targetProfile) {
        currentProfile = profileRepository.getById(currentProfile.getId());
        targetProfile = profileRepository.getById(targetProfile.getId());

        final Long targetProfileId = targetProfile.getId();

        FriendRequest friendRequest = friendRequestRepository.findByIdSourceAndIdTarget(currentProfile, targetProfile)
                .orElse(null);
        if (friendRequest != null) {
            if (friendRequest.getStatus() == FriendRequestStatus.ACCEPTED &&
                currentProfile.getFriends().stream().anyMatch(p -> Objects.equals(p.getId(), targetProfileId))) {
                throw new AlreadyExistsException(friendRequest.getId().getSource().getUsername() + "/"
                        + friendRequest.getId().getTarget().getUsername(), FriendRequest.class.getSimpleName());
            }
            if (LocalDateTime.from(friendRequestTimeoutPeriod.addTo(friendRequest.getCreateAt()))
                    .isBefore(LocalDateTime.now()) ||
                    friendRequest.getStatus() == FriendRequestStatus.REVOKE) {
                friendRequest.setStatus(FriendRequestStatus.PENDING);
                friendRequest.setCreateAt(LocalDateTime.now());
            } else {
                throw new AlreadyExistsException(friendRequest.getId().getSource().getUsername() + "/"
                        + friendRequest.getId().getTarget().getUsername(), FriendRequest.class.getSimpleName());
            }
        } else {
            friendRequestRepository.save(new FriendRequest(
                    new FriendRequest.FriendId(currentProfile, targetProfile),
                    FriendRequestStatus.PENDING,
                    LocalDateTime.now()));
        }
    }

    @Transactional
    public void revokeFriendRequest(Profile currentProfile, Profile targetProfile) {
        currentProfile = profileRepository.getById(currentProfile.getId());
        targetProfile = profileRepository.getById(targetProfile.getId());

        final String currentProfileUsername = currentProfile.getUsername();
        final String targetProfileUsername = targetProfile.getUsername();

        FriendRequest friendRequest = friendRequestRepository.findByIdSourceAndIdTarget(currentProfile, targetProfile)
            .filter(fr -> fr.getStatus() == FriendRequestStatus.PENDING)
            .orElseThrow(() -> new ObjectNotFoundException(
                String.format("%s/%s", currentProfileUsername, targetProfileUsername),
                FriendRequest.class.getSimpleName()));

        friendRequest.setStatus(FriendRequestStatus.REVOKE);
    }

    @Transactional
    public void acceptFriendRequest(Profile currentProfile, Profile targetProfile) {
        currentProfile = profileRepository.getById(currentProfile.getId());
        targetProfile = profileRepository.getById(targetProfile.getId());

        final String currentProfileUsername = currentProfile.getUsername();
        final String targetProfileUsername = targetProfile.getUsername();

        FriendRequest friendRequest = friendRequestRepository.findByIdSourceAndIdTarget(targetProfile, currentProfile)
                .filter(fr -> fr.getStatus() == FriendRequestStatus.PENDING)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("%s/%s", targetProfileUsername, currentProfileUsername),
                        FriendRequest.class.getSimpleName()));

        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);

        currentProfile.getFriends().add(targetProfile);
        targetProfile.getFriends().add(currentProfile);
    }

    @Transactional
    public void declineFriendRequest(Profile currentProfile, Profile targetProfile) {
        currentProfile = profileRepository.getById(currentProfile.getId());
        targetProfile = profileRepository.getById(targetProfile.getId());

        final String currentProfileUsername = currentProfile.getUsername();
        final String targetProfileUsername = targetProfile.getUsername();

        FriendRequest friendRequest = friendRequestRepository.findByIdSourceAndIdTarget(targetProfile, currentProfile)
                .filter(fr -> fr.getStatus() == FriendRequestStatus.PENDING)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("%s/%s", targetProfileUsername, currentProfileUsername),
                        FriendRequest.class.getSimpleName()));

        friendRequest.setStatus(FriendRequestStatus.DECLINED);
    }

    @Transactional
    public void removeProfileFriend(Profile currentProfile, Profile friend) {
        currentProfile = profileRepository.getById(currentProfile.getId());
        friend = profileRepository.getById(friend.getId());
        final Long profileSourceId = currentProfile.getId();
        final Long profileTargetId = friend.getId();

        if (currentProfile.getFriends().stream().noneMatch(profile -> Objects.equals(profile.getId(), profileTargetId))) {
            throw new SocialServiceException(ErrorCode.NOT_FRIEND);
        }

        currentProfile.getFriends().removeIf(currentFriend -> Objects.equals(currentFriend.getId(), profileTargetId));
        friend.getFriends().removeIf(currentFriend -> Objects.equals(currentFriend.getId(), profileSourceId));
    }

    private String buildFriendRequestId(FriendRequest friendRequest) {
        return friendRequest.getId().getSource().getUsername() + "/" + friendRequest.getId().getTarget().getUsername();
    }
}
