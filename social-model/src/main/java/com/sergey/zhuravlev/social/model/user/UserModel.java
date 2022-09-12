package com.sergey.zhuravlev.social.model.user;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class UserModel {

    private Long id;
    private String email;
    private String phone;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
