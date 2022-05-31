package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.enums.PrivacyScope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privacy_rules")
public class PrivacyRules {

    @Id
    @Column(name = "profile_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "allow_scope_to_write_messages")
    private PrivacyScope allowScopeToWriteMessages;

}
