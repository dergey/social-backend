package com.sergey.zhuravlev.social.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
