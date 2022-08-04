package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.converter.UUIDConverter;
import com.sergey.zhuravlev.social.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "new_users", indexes = {
        @Index(name = "IX_CONTINUATION_CODE", columnList = "continuation_code")
})
public class NewUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Convert(converter = UUIDConverter.class)
    @Column(name = "continuation_code", length = 36, nullable = false)
    private UUID continuationCode;

    @Column(name = "email", length = 191, updatable = false, unique = true)
    private String email;

    @Column(name = "phone", length = 15, updatable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_status", length = 20, nullable = false)
    private RegistrationStatus registrationStatus;

    @Embedded
    private Confirmation confirmation;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

}
