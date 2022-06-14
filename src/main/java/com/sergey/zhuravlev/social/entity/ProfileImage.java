package com.sergey.zhuravlev.social.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile_images")
public class ProfileImage {

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileImageId implements Serializable {
        @ManyToOne
        @JoinColumn(name = "profile_id")
        private Profile profile;
        @ManyToOne
        @JoinColumn(name = "image_id")
        private Image image;
    }

    @EmbeddedId
    private ProfileImageId id;

}
