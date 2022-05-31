package com.sergey.zhuravlev.social.converter;

import com.sergey.zhuravlev.social.enums.ImageSize;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, ImageSize> {
    @Override
    public ImageSize convert(String source) {
        return ImageSize.getPreviewSize(source);
    }
}