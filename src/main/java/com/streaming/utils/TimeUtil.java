package com.streaming.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

public final class TimeUtil {

    private TimeUtil() {

    }

    public static ZonedDateTime getCurrentUTCTime() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public static ZonedDateTime getUTCTime(final ZonedDateTime time) {
        return time.withZoneSameInstant(ZoneId.of("UTC"));
    }

    public static ZonedDateTime getUTCTime(final Date date) {
        return date.toInstant().atZone(ZoneId.of("UTC"));
    }

    public static ZonedDateTime getUTCTime(final LocalDateTime time) {
        return time.atZone(ZoneId.of("UTC"));
    }

    public static LocalDateTime getStringUTCTime(final String time) {
        return LocalDateTime.parse(time, RFC_1123_DATE_TIME);
    }
}
