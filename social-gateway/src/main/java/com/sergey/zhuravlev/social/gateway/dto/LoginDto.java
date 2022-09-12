package com.sergey.zhuravlev.social.gateway.dto;

import com.sergey.zhuravlev.social.gateway.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "test@test.com")
    @Email(message = ErrorCode.INVALID_EMAIL_FORMAT_CODE)
    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    @Size(min = 1, max = 191, message = ErrorCode.WRONG_LENGTH_CODE)
    private String email;

    @Schema(example = "qwerty")
    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    @Size(min = 4, max = 100, message = ErrorCode.WRONG_LENGTH_CODE)
    private String password;

    @Schema(description = "If true, the issued token will be valid for a longer time (not safe for short sessions)", example = "false")
    private boolean rememberMe;

}
