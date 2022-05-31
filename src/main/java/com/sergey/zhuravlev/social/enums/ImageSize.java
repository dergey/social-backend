package com.sergey.zhuravlev.social.enums;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ImageSize {

    _50x50("50x50", 50, 50),
    _150x150("150x150", 150, 150),
    _256x256("256x256", 256, 256);

    private final String size;
    private final Integer height;
    private final Integer width;

    private static final Map<String, ImageSize> stringValues;
    static {
        final Map<String, ImageSize> map = Maps.newHashMap();
        for(final ImageSize enumerate : ImageSize.values()) {
            map.put(enumerate.getSize(), enumerate);
        }
        stringValues = ImmutableMap.copyOf(map);
    }

    public static ImageSize getPreviewSize(String value) {
        if(!stringValues.containsKey(value)) {
            throw new IllegalArgumentException("Unknown String Value: " + value);
        }
        return stringValues.get(value);
    }

    @Override
    public String toString() {
        return size;
    }

}
