package com.sergey.zhuravlev.social.util;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ImageResponseUtils {

    private static final DateTimeFormatter LAST_MODIFIED_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
    private static final ZoneId LAST_MODIFIED_ZONE_ID = ZoneId.of("GMT");

    public static boolean checkNotModified(WebRequest request, long lastModifiedTimestamp) {
        String lastModifiedHeaderValue = request.getHeader(HttpHeaders.LAST_MODIFIED);
        if (lastModifiedHeaderValue == null) {
            return false;
        }
        ZonedDateTime dateTime = ZonedDateTime.parse(lastModifiedHeaderValue, LAST_MODIFIED_DATE_TIME_FORMATTER);
        Long lastModifiedTimestampHeaderValue = dateTime.toInstant().toEpochMilli();
        return lastModifiedTimestampHeaderValue.equals(lastModifiedTimestamp);
    }

    public static Long toLastModifiedTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(LAST_MODIFIED_ZONE_ID).withNano(0).toInstant().toEpochMilli();
    }

}
