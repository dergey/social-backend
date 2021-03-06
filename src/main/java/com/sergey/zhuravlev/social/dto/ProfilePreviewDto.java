package com.sergey.zhuravlev.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePreviewDto {

    private String username;
    private String firstName;
    private String middleName;
    private String secondName;

}
