package com.sergey.zhuravlev.social.dto;

import com.sergey.zhuravlev.social.enums.AddressType;
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
public class UpdateAddressDto {

    @NotNull
    @NotBlank
    private AddressType type;

    @NotBlank
    private String firstLine;

    @NotBlank
    private String secondLine;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String country;

    @NotBlank
    private String zipCode;

}
