package com.sergey.zhuravlev.social.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.Page;

import java.io.IOException;

@SuppressWarnings("rawtypes")
public class PageSerializer extends StdSerializer<Page> {

    public PageSerializer() {
        this(null);
    }

    public PageSerializer(Class<Page> t) {
        super(t);
    }

    @Override
    public void serialize(
            Page value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("totalPages", value.getTotalPages());
        jgen.writeNumberField("totalElements", value.getTotalElements());
        jgen.writeNumberField("size", value.getSize());
        jgen.writeNumberField("number", value.getNumber());
        jgen.writeBooleanField("hasNext", value.hasNext());
        jgen.writeObjectField("content", value.getContent());
        jgen.writeEndObject();
    }
}
