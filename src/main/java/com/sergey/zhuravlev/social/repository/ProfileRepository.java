package com.sergey.zhuravlev.social.repository;

import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, QuerydslPredicateExecutor<Profile> {

    Optional<Profile> findByUsername(String username);

    Optional<Profile> findByUser(User user);

//    // profile sent requests (where profile is the source)
//    @Query(value = "select fr.id.target from FriendRequest fr where fr.id.source = :profile and fr.status = 'PENDING'",
//            countQuery = "select count(fr) from FriendRequest fr where fr.id.source = :profile and fr.status = 'PENDING'")
//    Page<Profile> findAllByProfileSourceInFriendRequest(@Param("profile") Profile profile, Pageable pageable);

    // profile incoming requests (where profile is the target)
    // todo add the ability to sort: From new to old, From old to new
    @Query(value = "select fr.id.source from FriendRequest fr where fr.id.target = :profile and fr.status = 'PENDING' order by fr.createAt asc",
            countQuery = "select count(fr) from FriendRequest fr where fr.id.target = :profile and fr.status = 'PENDING'")
    Page<Profile> findAllByProfileTargetInFriendRequest(@Param("profile") Profile profile, Pageable pageable);

    @Query(value = "select p.friends from Profile p where p = :profile",
            countQuery = "select count(p.friends) from Profile p where p = :profile")
    Page<Profile> findAllByFriendInProfile(@Param("profile") Profile profile, Pageable pageable);

}
