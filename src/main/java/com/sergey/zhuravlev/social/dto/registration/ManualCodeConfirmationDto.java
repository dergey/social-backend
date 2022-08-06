package com.sergey.zhuravlev.social.dto.registration;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ManualCodeConfirmationDto {

    @NotNull(message = ErrorCode.NOT_NULL_CODE)
    private UUID continuationCode;

    @Schema(example = "11111")
    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String manualCode;

}
