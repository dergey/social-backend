package com.sergey.zhuravlev.social.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private String username;
    private String firstName;
    private String middleName;
    private String secondName;

}
