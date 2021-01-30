package org.xyx.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 *
 * jdk1.8及以上
 *
 * @author xueyongxin
 */
public enum DateFormatUtil {

    DATE("yyyy-MM-dd"),
    DATE_SLASH("yyyy/MM/dd"),
    DATE_NOT_SPLIT("yyyyMMdd"),
    DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    DATE_TIME_H("yyyy-MM-dd HH"),
    DATE_TIME_HM("yyyy-MM-dd HH:mm"),
    DATE_TIME_SLASH("yyyy/MM/dd HH:mm:ss"),
    DATE_TIME_SLASH_H("yyyy/MM/dd HH"),
    DATE_TIME_SLASH_HM("yyyy/MM/dd HH:mm"),
    DATE_TIME_SLASH_HMSS("yyyy/MM/dd-HH:mm:ss:SSS"),
    DATE_TIME_NOT_SPLIT("yyyyMMddHHmmss"),
    ;


    private final String pattern;

    private final DateTimeFormatter formatter;

    DateFormatUtil(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }


    public String getPattern() {
        return pattern;
    }


    public String parse(Date date) {
        return formatter.format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }


    public Date parse(String str) {
        return Date.from(toZonedDateTime(str).toInstant());
    }


    private LocalDateTime toLocalDateTime(String str) {
        try {
            // todo DATE\DATE_SLASH\DATE_NOT_SPLIT 解析失败
            return LocalDateTime.parse(str, formatter);
        } catch (DateTimeParseException e) {
            throw e;
//            String info = String.format("%s could not be parsed at index %s, pattern:%s",
//                    e.getParsedString(), e.getErrorIndex(), pattern);
//            throw new DateTimeParseException(info, e.getParsedString(), e.getErrorIndex());
        }
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    private ZonedDateTime toZonedDateTime(String str) {
        return toLocalDateTime(str).atZone(ZoneId.systemDefault());
    }


    public String addHours(Date date, long hour) {
        return formatter.format(toLocalDateTime(date).plusHours(hour));
    }

    public String addHours(String date, long hour) {
        return formatter.format(toLocalDateTime(date)
                .plusHours(hour)
                .atZone(ZoneId.systemDefault()));
    }


    public String addMinutes(Date date, long minutes) {
        return formatter.format(toLocalDateTime(date).plusMinutes(minutes));

    }

    public String addMinutes(String date, long minutes) {
        return formatter.format(toLocalDateTime(date)
                .plusMinutes(minutes)
                .atZone(ZoneId.systemDefault()));
    }


    public String addSeconds(Date date, long seconds) {
        return formatter.format(toLocalDateTime(date).plusSeconds(seconds));
    }


    public String addSeconds(String date, long seconds) {
        return formatter.format(toLocalDateTime(date)
                .plusSeconds(seconds)
                .atZone(ZoneId.systemDefault()));
    }


    public String addNanos(Date date, long nanos) {
        return formatter.format(toLocalDateTime(date).plusNanos(nanos));
    }

    public String addNanos(String date, long nanos) {
        return formatter.format(toLocalDateTime(date)
                .plusNanos(nanos)
                .atZone(ZoneId.systemDefault()));
    }


    public String addYears(Date date, long years) {
        return formatter.format(toLocalDateTime(date).plusYears(years));
    }

    public String addYears(String date, long years) {
        return formatter.format(toLocalDateTime(date)
                .plusYears(years)
                .atZone(ZoneId.systemDefault()));
    }


    public String addMonths(Date date, long months) {
        return formatter.format(toLocalDateTime(date).plusMonths(months));
    }


    public String addMonths(String date, long months) {
        return formatter.format(toLocalDateTime(date)
                .plusMonths(months)
                .atZone(ZoneId.systemDefault()));
    }


    public String addWeeks(Date date, long weeks) {
        return formatter.format(toLocalDateTime(date).plusWeeks(weeks));
    }


    public String addWeeks(String date, long weeks) {
        return formatter.format(toLocalDateTime(date)
                .plusWeeks(weeks)
                .atZone(ZoneId.systemDefault()));
    }


    public String addDays(Date date, long days) {
        return formatter.format(toLocalDateTime(date).plusDays(days));
    }


    public String addDays(String date, long days) {
        return formatter.format(toLocalDateTime(date)
                .plusDays(days)
                .atZone(ZoneId.systemDefault()));
    }

    public int getDayOfMonth(String date) {
        return toLocalDateTime(date).getDayOfMonth();
    }

    public int getMonth(String date) {
        return toLocalDateTime(date).getMonthValue();
    }

    public int getYear(String date) {
        return toLocalDateTime(date).getYear();
    }

    public String getDayOfWeek(String date) {
        String[] weekOfDays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        LocalDateTime ldt = toLocalDateTime(date);
        int day = toLocalDateTime(date).getDayOfWeek().getValue();
        return weekOfDays[day - 1];
    }

    public String getDateStrByMillis(long millis) {
        return parse(new Date(millis));
    }

    public String getUtcTimeString() {
        return getZoneDateFormatted(ZoneOffset.UTC);
    }

    public String getZoneDateFormatted(ZoneId zoneId) {
        return formatter.format(LocalDateTime.now(zoneId));
    }



}
