package com.sergey.zhuravlev.social.dto.registration;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContinuationDto {

    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    private UUID continuationCode;

}
