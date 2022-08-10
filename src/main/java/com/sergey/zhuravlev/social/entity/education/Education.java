package com.sergey.zhuravlev.social.entity.education;

import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.enums.EducationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "educations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private EducationType type;

    @Column(name = "country", length = 2, nullable = false)
    private String country;

    @Column(name = "city", length = 180, nullable = false)
    private String city;

    @Column(name = "institution", length = 180, nullable = false)
    private String institution;

    @Column(name = "group", length = 20)
    private String group;

    @Column(name = "specialty", length = 60)
    private String specialty;

}
