package com.sergey.zhuravlev.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @Email
    @Length(max = 191)
    private String email;

    @Length(max = 100)
    @NotBlank
    private String password;


    @Length(max = 60)
    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String secondName;

    @Length(max = 100)
    @NotBlank
    private String city;

    @Past
    private LocalDate birthDate;

}
