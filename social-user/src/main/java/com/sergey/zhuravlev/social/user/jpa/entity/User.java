package com.sergey.zhuravlev.social.user.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Long id;

    @Column("email")
    private String email;

    @Column("phone")
    private String phone;

    @Column("password_hash")
    private String password;

    @Column("confirmation_role")
    private ConfirmationRole confirmationRole;

    @Embedded.Nullable(prefix = "confirmation")
    private Confirmation confirmation;

    @Column("create_at")
    private LocalDateTime createAt;

    @Column("update_at")
    private LocalDateTime updateAt;

}
