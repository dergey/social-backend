package com.sergey.zhuravlev.social.dto.registration;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    @Schema(example = "qwerty")
    @Length(max = 100, message = ErrorCode.WRONG_LENGTH_CODE)
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String password;

    @Schema(example = "test")
    @Length(max = 60, message = ErrorCode.WRONG_LENGTH_CODE)
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String username;

    @Schema(example = "Hiroko")
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String firstName;

    @Schema(example = "Shingo")
    private String middleName;

    @Schema(example = "Hamasaki")
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String secondName;

    @Schema(example = "MALE")
    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    private Gender gender;

    @Schema(example = "1970-01-01", format = "past")
    @Past(message = ErrorCode.PAST_REQUIRED_CODE)
    private LocalDate birthDate;

}
