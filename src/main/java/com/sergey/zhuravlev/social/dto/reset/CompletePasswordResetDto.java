package com.sergey.zhuravlev.social.dto.reset;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletePasswordResetDto {

    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    private UUID continuationCode;

    @Schema(example = "qwerty")
    @Length(max = 100, message = ErrorCode.WRONG_LENGTH_CODE)
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String password;

}
