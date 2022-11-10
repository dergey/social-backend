package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ProfileAttitude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePreviewDto {

    private String username;
    private String firstName;
    private String middleName;
    private String secondName;
    private String city;
    private LocalDate birthDate;
    private ProfileAttitude attitude;

}
