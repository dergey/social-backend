package com.sergey.zhuravlev.social.service;

import com.google.common.collect.Iterables;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.enums.ProfileAttitude;
import com.sergey.zhuravlev.social.repository.FriendRequestRepository;
import com.sergey.zhuravlev.social.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProfileAttitudeService {

    private final ProfileRepository profileRepository;
    private final FriendRequestRepository friendRequestRepository;

    public Profile setAttitude(Profile target, Profile profileAspect) {
        if (Objects.equals(target.getId(), profileAspect.getId())) {
            target.setAttitude(ProfileAttitude.YOU);
            return target;
        }

        boolean isFriend = profileRepository.existsByIdAndFriendsContains(target.getId(), profileAspect);
        boolean isFriendIncoming = friendRequestRepository.existsByIdSourceIdAndIdTarget(target.getId(), profileAspect);
        boolean isFriendOutgoing = friendRequestRepository.existsByIdTargetIdAndIdSource(target.getId(), profileAspect);

        if (isFriend) {
            target.setAttitude(ProfileAttitude.FRIEND);
            return target;
        } else if (isFriendIncoming) {
            target.setAttitude(ProfileAttitude.FRIEND_INCOMING);
            return target;
        } else if (isFriendOutgoing) {
            target.setAttitude(ProfileAttitude.FRIEND_OUTGOING);
            return target;
        } else {
            target.setAttitude(ProfileAttitude.NONE);
            return target;
        }
    }

    public Profile setAttitudeForce(Profile target, ProfileAttitude attitude) {
        target.setAttitude(attitude);
        return target;
    }

    public <T extends Iterable<Profile>> T setAttitudes(T targets, Profile profileAspect) {
        if (Iterables.isEmpty(targets)) {
            return targets;
        }

        for (Profile target : targets) {
            if (Objects.equals(target.getId(), profileAspect.getId())) {
                target.setAttitude(ProfileAttitude.YOU);
            }
        }

        List<Long> ids = StreamSupport.stream(targets.spliterator(), false)
            .map(Profile::getId)
            .collect(Collectors.toList());

        Collection<Long> friendIds = profileRepository.findAllIdInAndFriendsContains(ids, profileAspect);
        Collection<Long> friendIncomingIds = friendRequestRepository.findAllIdSourceIdInAndIdTarget(ids, profileAspect);
        Collection<Long> friendOutgoingIds = friendRequestRepository.findAllIdTargetIdInAndIdSource(ids, profileAspect);

        for (Profile target : targets) {
            if (friendIds.contains(target.getId())) {
                target.setAttitude(ProfileAttitude.FRIEND);
            } else if (friendIncomingIds.contains(target.getId())) {
                target.setAttitude(ProfileAttitude.FRIEND_INCOMING);
            } else if (friendOutgoingIds.contains(target.getId())) {
                target.setAttitude(ProfileAttitude.FRIEND_OUTGOING);
            } else {
                target.setAttitude(ProfileAttitude.NONE);
            }
        }

        return targets;
    }

    public <T extends Iterable<Profile>> T setAttitudesForce(T targets, ProfileAttitude attitude) {
        for (Profile target : targets) {
            target.setAttitude(attitude);
        }
        return targets;
    }

}
