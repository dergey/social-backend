package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.enums.RelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private User user;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "avatar")
    private Image avatar;

    @ManyToMany
    @JoinTable(
            name="friends",
            joinColumns = @JoinColumn(name="profile_id"),
            inverseJoinColumns = @JoinColumn(name="profile_friend_id")
    )
    private Collection<Profile> friends;

    @OneToMany
    @JoinTable(
            name="profile_images",
            joinColumns = @JoinColumn(name="profile_id"),
            inverseJoinColumns = @JoinColumn(name="image_id")
    )
    private Collection<Image> profileImages;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "overview", length = 500)
    private String overview;

    @Column(name = "relationship_status", length = 20)
    private RelationshipStatus relationshipStatus;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "workplace", length = 100)
    private String workplace;

    @Column(name = "education", length = 100)
    private String education;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "search_string", nullable = false)
    private String searchString;

}
