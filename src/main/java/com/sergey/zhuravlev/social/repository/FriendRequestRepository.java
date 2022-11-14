package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.FriendRequest;
import com.sergey.zhuravlev.social.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, FriendRequest.FriendId> {

    Optional<FriendRequest> findByIdSourceAndIdTarget(Profile currentProfile, Profile targetProfile);

    boolean existsByIdSourceIdAndIdTarget(Long id, Profile aspectProfile);

    boolean existsByIdTargetIdAndIdSource(Long id, Profile aspectProfile);

    @Query("select fr.id.source.id from FriendRequest fr where fr.id.target = :profile and fr.status = 'PENDING' and fr.id.source.id in :profileIds")
    Collection<Long> findAllIdSourceIdInAndIdTarget(@Param("profileIds") List<Long> ids, @Param("profile") Profile profileAspect);

    @Query("select fr.id.target.id from FriendRequest fr where fr.id.source = :profile and fr.status = 'PENDING' and fr.id.target.id in :profileIds")
    Collection<Long> findAllIdTargetIdInAndIdSource(@Param("profileIds") List<Long> ids, @Param("profile") Profile profileAspect);
}