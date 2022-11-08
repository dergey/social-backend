package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.PrivacyScope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePrivatePropertyDto<T> {

    @NotNull
    private T value;

    @NotNull
    private PrivacyScope privacyRule;

}
