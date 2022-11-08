package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSubmissionDto {

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

}
