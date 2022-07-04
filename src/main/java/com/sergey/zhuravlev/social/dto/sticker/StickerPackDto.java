package com.sergey.zhuravlev.social.dto.sticker;

import com.sergey.zhuravlev.social.dto.ProfilePreviewDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StickerPackDto {

    private Long id;
    private ProfilePreviewDto author;
    private String title;
    private Integer stickerTotal;

}
