package com.sergey.zhuravlev.social.dto.registration;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRegistrationDto {

    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    private UUID continuationCode;

    @Length(max = 100, message = ErrorCode.WRONG_LENGTH_CODE)
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String password;

    @Length(max = 60, message = ErrorCode.WRONG_LENGTH_CODE)
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String username;

    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String firstName;

    private String middleName;

    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String secondName;

    @Length(max = 100, message = ErrorCode.WRONG_LENGTH_CODE)
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String city;

    @Past(message = ErrorCode.PAST_REQUIRED_CODE)
    private LocalDate birthDate;

}
