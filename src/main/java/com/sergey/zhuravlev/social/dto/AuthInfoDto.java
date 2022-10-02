package com.sergey.zhuravlev.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfoDto {

    private String issuer;
    private String clientId;

}
