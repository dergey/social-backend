package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldsErrorDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FieldError {
        @Schema(example = "email")
        private final String field;
        @Schema(example = "NOT_BLANK")
        private final String code;
    }

    @Schema(example = "NOT_VALID", allowableValues = {"FIELD_ERROR", "NOT_VALID"})
    private ErrorCode code;
    private Collection<FieldError> fields;

}
