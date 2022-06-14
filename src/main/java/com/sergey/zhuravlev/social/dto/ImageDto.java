package com.sergey.zhuravlev.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private String mimeType;

    private Integer height;

    private Integer width;

    private String dataSize;

    private LocalDateTime createAt;

}
