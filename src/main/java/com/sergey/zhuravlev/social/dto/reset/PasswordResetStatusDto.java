package com.sergey.zhuravlev.social.dto.reset;

import com.sergey.zhuravlev.social.enums.PasswordResetStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetStatusDto {

    private PasswordResetStatus status;
    private UUID continuationCode;

}
