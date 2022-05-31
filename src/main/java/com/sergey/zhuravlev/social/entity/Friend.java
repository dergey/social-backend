package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.enums.FriendRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friends")
public class Friend {

    @Getter
    @Setter
    @Embeddable
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

    @Column(name = "status", nullable = false)
    private FriendRequestStatus status;

}
