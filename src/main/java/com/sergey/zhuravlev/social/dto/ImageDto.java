package com.sergey.zhuravlev.social.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "image/jpeg")
    private String mimeType;

    @Schema(example = "256")
    private Integer height;

    @Schema(example = "256")
    private Integer width;

    @Schema(example = "200KB")
    private String dataSize;

    private LocalDateTime createAt;

}
