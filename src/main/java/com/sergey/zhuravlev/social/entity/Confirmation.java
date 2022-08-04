package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.enums.ConfirmationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Confirmation {

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ConfirmationType type;

    @Column(name = "manual_code", length = 40)
    private String manualCode;

    @Column(name = "manual_code_tries")
    private Integer manualCodeTries;

    @Column(name = "link_code", length = 40)
    private String linkCode;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

}
