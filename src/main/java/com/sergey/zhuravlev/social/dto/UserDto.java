package com.sergey.zhuravlev.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String email;

    private String username;

    private ImageDto avatar;

    private Collection<String> images;

    private String firstName;

    private String middleName;

    private String secondName;

    private LocalDate birthDate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
