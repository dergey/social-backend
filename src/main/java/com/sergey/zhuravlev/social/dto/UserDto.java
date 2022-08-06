package com.sergey.zhuravlev.social.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(example = "test@test.com")
    private String email;

    @Schema(example = "test")
    private String username;

    private ImageDto avatar;

    @ArraySchema(schema = @Schema(example = "1"))
    private Collection<String> images;

    @Schema(example = "Hiroko")
    private String firstName;

    @Schema(example = "Shingo")
    private String middleName;

    @Schema(example = "Hamasaki")
    private String secondName;

    @Schema(example = "1970-01-01")
    private LocalDate birthDate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
