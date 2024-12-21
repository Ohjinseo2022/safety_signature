package com.safety_signature.safety_signature_back.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
public class DateUtil {


    public static String nowDate() {
        return nowDate("yyyy-MM-dd");
    }

    public static String nowDate(String formatterPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterPattern);
        return DateUtil.nowForLocalDate().format(formatter);

    }

    public static LocalDate nowForLocalDate() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"));
    }

    public static String nowDatetime() {
        return nowDatetime("yyyy-MM-dd HH:mm:ss");
    }

    public static String nowDatetime(String formatterPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatterPattern);
        return DateUtil.nowForLocalDateTime().format(formatter);

    }

    public static LocalDateTime nowForLocalDateTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public static Instant stringDateTimeToInstant(String targetDateTime,String targetFormatter){
        LocalDateTime startDateLocalDateTime = LocalDateTime.parse(targetDateTime,DateTimeFormatter.ofPattern(targetFormatter));
        return startDateLocalDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant();
    }

    public static String instantToStringDate(Instant instant, String format) {
        String result = null;
        try {
            result = instant.atZone(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static LocalDateTime stringDateTimeToLocalDateTime(String stringDateTime, String format) {
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(stringDateTime, DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
        return localDateTime;
    }

    public static LocalDate stringDateToLocalDate(String stringDate, String format) {
        LocalDate localDate = null;
        try {
            localDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
        return localDate;
    }

    public static LocalDate stringDateToLocalDateDefault(String stringDate, String format) {
        LocalDate localDate = stringDateToLocalDate(stringDate, format);
        if(localDate == null){
            return LocalDate.now();
        }
        return localDate;
    }

    public static String localDateTimeToStringDateTime(LocalDateTime localDateTime, String format) {
        String stringLocalDateTime = null;
        try {
            stringLocalDateTime = localDateTime.format(DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
        return stringLocalDateTime;
    }
    public static String localDateTimeToStringDate(LocalDateTime localDateTime, String format) {
        String stringLocalDate = null;
        try {
            stringLocalDate = localDateTime.format(DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
        return stringLocalDate;
    }

    public static String localDateToStringDate(LocalDate localDate, String format) {
        String stringLocalDate = null;
        try {
            stringLocalDate = localDate.format(DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
        return stringLocalDate;
    }

    public static LocalDateTime localDateTimeToUTC(LocalDateTime localDateTime) {
        return ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Seoul")).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public static Long getLocalDateDday(LocalDate toDate) {
        if (toDate == null) {
            return null;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), toDate);
    }

    public static Instant localDateTimeToInstant(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant();
    }

    public static Integer localdateDiffGetMonths(LocalDate source, LocalDate Target){
        Long r = ChronoUnit.MONTHS.between(source.minusMonths(1L),Target);
        return Integer.parseInt(r.toString());
    }

    public static LocalDate lastDayOfMonth(LocalDate source){
        return source.withDayOfMonth(source.lengthOfMonth());
    }

    public static LocalDate firstDayOfMonth(LocalDate source){
        LocalDate ldm = lastDayOfMonth(source.minusMonths(1L));
        return ldm.plusDays(1L);
    }

}
