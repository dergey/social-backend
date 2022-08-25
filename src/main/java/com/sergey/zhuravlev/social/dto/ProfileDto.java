package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.RelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private String username;
    private ImageDto avatar;
    private String firstName;
    private String middleName;
    private String secondName;
    private String overview;
    private RelationshipStatus relationshipStatus;
    private String city;
    private String workplace;
    private String education;
    private LocalDate birthDate;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
