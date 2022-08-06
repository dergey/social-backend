package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    @Schema(example = "UNKNOWN_ERROR")
    private ErrorCode code;
    @Schema(example = "Unknown error")
    private String message;

}
