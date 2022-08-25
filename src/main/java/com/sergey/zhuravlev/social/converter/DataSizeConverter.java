package com.sergey.zhuravlev.social.converter;

import org.springframework.util.unit.DataSize;

import javax.persistence.AttributeConverter;

public class DataSizeConverter implements AttributeConverter<DataSize, String> {

    @Override
    public String convertToDatabaseColumn(DataSize dataSize) {
        if (dataSize != null) {
            return dataSize.toString();
        } else {
            return null;
        }
    }

    @Override
    public DataSize convertToEntityAttribute(String s) {
        if (s != null) {
            return DataSize.parse(s);
        } else {
            return null;
        }
    }
}