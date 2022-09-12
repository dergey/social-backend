package com.sergey.zhuravlev.social.user.jpa.entity;

import com.sergey.zhuravlev.social.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "new_users")
public class NewUser {

    @Id
    private Long id;

    @Column("continuation_code")
    private UUID continuationCode;

    @Column("email")
    private String email;

    @Column("phone")
    private String phone;

    @Column("registration_status")
    private RegistrationStatus registrationStatus;

    @Embedded.Nullable(prefix = "confirmation")
    private Confirmation confirmation;

    @Column("create_at")
    private LocalDateTime createAt;

}
