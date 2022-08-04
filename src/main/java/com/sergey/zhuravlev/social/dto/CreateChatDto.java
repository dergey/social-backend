package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatDto {

    @NotBlank(message = ErrorCode.NOT_BLANK_CODE)
    private String targetUsername;

}
