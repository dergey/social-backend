package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.FriendRequest;
import com.sergey.zhuravlev.social.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, FriendRequest.FriendId> {

    Optional<FriendRequest> findByIdSourceAndIdTarget(Profile currentProfile, Profile targetProfile);

}