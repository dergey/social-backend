package com.sergey.zhuravlev.social.dto.registration;

import com.sergey.zhuravlev.social.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationStatusDto {

    private RegistrationStatus status;
    private UUID continuationCode;

}
