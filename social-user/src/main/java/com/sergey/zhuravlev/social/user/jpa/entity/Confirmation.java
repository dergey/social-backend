package com.sergey.zhuravlev.social.user.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Confirmation {

    @Column("type")
    private ConfirmationType type;

    @Column("manual_code")
    private String manualCode;

    @Column("manual_code_tries")
    private Integer manualCodeTries;

    @Column("link_code")
    private String linkCode;

    @Column("valid_until")
    private LocalDateTime validUntil;

}
