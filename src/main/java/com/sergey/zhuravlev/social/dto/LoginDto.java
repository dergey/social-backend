package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Email(message = ErrorCode.INVALID_EMAIL_FORMAT_CODE)
    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    @Size(min = 1, max = 191, message = ErrorCode.WRONG_LENGTH_CODE)
    private String email;

    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    @Size(min = 4, max = 100, message = ErrorCode.WRONG_LENGTH_CODE)
    private String password;

    private boolean rememberMe;

}
