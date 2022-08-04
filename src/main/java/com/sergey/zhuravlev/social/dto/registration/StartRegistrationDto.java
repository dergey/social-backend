package com.sergey.zhuravlev.social.dto.registration;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.validation.EmailOrPhone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StartRegistrationDto {

    @EmailOrPhone(message = ErrorCode.INVALID_EMAIL_OR_PHONE_FORMAT_CODE)
    private String phoneOrEmail;

}
