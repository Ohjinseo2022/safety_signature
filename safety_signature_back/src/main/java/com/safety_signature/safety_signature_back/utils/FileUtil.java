package com.safety_signature.safety_signature_back.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

@Slf4j
public class FileUtil {

    public static long BYTE = 1L;
    public static long KiB = BYTE << 10;
    public static long MiB = KiB << 10;
    public static long GiB = MiB << 10;
    public static long TiB = GiB << 10;
    public static long PiB = TiB << 10;
    public static long EiB = PiB << 10;

    private static DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");

    public static String formatSize(long size, long divider, String unitName) {
        return DEC_FORMAT.format((double) size / divider) + " " + unitName;
    }

    public static Boolean fileSizeCheck(long targetSize, long divider, Integer checkSize) {
        return targetSize > (checkSize * divider) ? false : true;
    }

    public static BigDecimal getFileSize(long fileSize) {
        return BigDecimal.valueOf(fileSize).divide(new BigDecimal("1024"), 5, BigDecimal.ROUND_UP);
    }

    public static String getFileNameFormat(String fileName) {
        if (fileName == null) {
            return "";
        }
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        return fileName.replaceAll("\\+", "%20");
    }

    public static String getFileDisposition(String fileName) {
        fileName = getFileNameFormat(fileName);
        if (fileName == null) {
            return "";
        }
        return String.format("attachment; filename=\"%s\";", fileName);
    }

    public static String getZipFileDisposition(String fileName) {
        fileName = getFileNameFormat(fileName);
        fileName = getCompressedFileBaseName(fileName);
        if (fileName == null) {
            return "";
        }
        return String.format("attachment; filename=\"%s\";", fileName);
    }

    public static String getCompressedFileBaseName(String fileName){
        return String.format("%s.zip", FileNameUtils.getBaseName(fileName));
    }

}
