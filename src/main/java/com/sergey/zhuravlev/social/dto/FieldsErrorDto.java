package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ErrorCode;
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
        private final String field;
        private final String message;
    }

    private ErrorCode code;
    private Collection<FieldError> fields;

}
