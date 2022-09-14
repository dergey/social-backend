package com.sergey.zhuravlev.social.dto.reset;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.validation.EmailOrPhone;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartPasswordResetDto {

    @Schema(format = "\\d{7,15}|\\w+@\\w+\\.\\w{2,3}", example = "375111111111")
    @EmailOrPhone(message = ErrorCode.INVALID_EMAIL_OR_PHONE_FORMAT_CODE)
    private String phoneOrEmail;


}
