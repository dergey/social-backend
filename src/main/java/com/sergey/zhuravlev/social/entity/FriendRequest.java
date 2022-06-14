package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.enums.FriendRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend_requests")
public class FriendRequest {

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "source_profile_id")
        private Profile source;
        @ManyToOne
        @JoinColumn(name = "target_profile_id")
        private Profile target;
    }

    @EmbeddedId
    private FriendId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private FriendRequestStatus status;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

}
