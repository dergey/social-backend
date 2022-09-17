package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.converter.UUIDConverter;
import com.sergey.zhuravlev.social.enums.PasswordResetStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "password_resets", indexes = {
        @Index(name = "ix_password_reset_continuation_code", columnList = "continuation_code")
})
public class PasswordReset {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "password_reset_status", length = 20, nullable = false)
    private PasswordResetStatus status;

    @Convert(converter = UUIDConverter.class)
    @Column(name = "continuation_code", length = 36, nullable = false)
    private UUID continuationCode;

    @Embedded
    private Confirmation confirmation;

}
