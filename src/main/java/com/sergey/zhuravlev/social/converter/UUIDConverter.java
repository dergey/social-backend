package com.sergey.zhuravlev.social.converter;

import javax.persistence.AttributeConverter;
import java.util.UUID;

public class UUIDConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        if (uuid != null) {
            return uuid.toString();
        } else {
            return null;
        }
    }

    @Override
    public UUID convertToEntityAttribute(String s) {
        if (s != null) {
            return UUID.fromString(s);
        } else {
            return null;
        }
    }
}